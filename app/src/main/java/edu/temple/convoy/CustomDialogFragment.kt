package edu.temple.convoy

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import org.json.JSONObject

class CustomDialogFragment(_dialogText: String, _context: Context) : DialogFragment() {
    val dialogText = _dialogText
    private val context = _context
    private val codeEditText = EditText(context)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(dialogText)
            .setView(codeEditText)
            .setPositiveButton("OK") { _,_ ->
                val params = HashMap<String, String>()
                params["action"] = "CREATE"
                params["username"] = "NR"
                params["session_key"] = codeEditText.text.toString()
                Utils().getDataFromAPI("https://kamorris.com/lab/convoy/convoy.php", context, params){
                    val jsonData = JSONObject(it)
                    if(jsonData.getString("status") == "SUCCESS"){
                        Log.d("API",jsonData.getString("convoy_id"))

                        //convoyID = jsonData.getString("convoy_id")
                        //supportActionBar?.title = convoyID
                    } else{
                        Log.d("API",jsonData.getString("message"))
                    }
                }
            }
            .setNegativeButton("Cancel"){_,_ ->}
            .create()

    companion object {
        const val TAG = "JoinConvoy"
    }
}