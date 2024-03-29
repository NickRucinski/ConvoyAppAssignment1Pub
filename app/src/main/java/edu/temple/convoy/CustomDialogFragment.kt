package edu.temple.convoy

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import org.json.JSONObject

class CustomDialogFragment(val dialogText: String, val username: String, val enableEditText: Boolean) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext()).apply {
            setMessage(dialogText)
            if(enableEditText){
                val codeEditText = EditText(context)
                setView(codeEditText)
                setPositiveButton("OK") { _,_ ->
                    val params = HashMap<String, String>()
                    params["action"] = "CREATE"
                    params["username"] = username
                    params["session_key"] = codeEditText.text.toString()
                    Utils().getDataFromAPI("https://kamorris.com/lab/convoy/convoy.php", requireContext(), params){
                        val jsonData = JSONObject(it)
                        if(jsonData.getString("status") == "SUCCESS"){
                            Log.d("API",jsonData.getString("convoy_id"))
                        } else{
                            Log.d("API",jsonData.getString("message"))
                        }
                    }
                }
            } else{
                setPositiveButton("OK") { _,_ -> }
            }
            setNegativeButton("Cancel"){_,_ ->}
        }.create()


    companion object {
        const val TAG = "JoinConvoy"
    }
}