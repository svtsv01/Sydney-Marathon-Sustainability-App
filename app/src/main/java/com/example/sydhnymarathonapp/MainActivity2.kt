package com.example.sydhnymarathonapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import com.example.sydhnymarathonapp.databinding.ActivityMain2Binding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MainActivity2 : ComponentActivity() {

    private var textResult = mutableStateOf("")

    private val client = OkHttpClient()

    private fun getResponse(barCodeNumber: String)
    {
        val url = "https://api.openai.com/v1/chat/completions"
        val chatGPTKey = "sk-proj-vE16jNFClJPOKD1CvYvQ1LAYo2tNN3NEgg3_WxYL0H0gBnTRYmJxTqcF5onajGVLDmQu0F" +
                "wD2NT3BlbkFJKE50xJ7VdwJWKlmiF-CGYIs3NHLDrmVbyrNdCoBOTL8MUA08IXlt8R7HC82ovHRDpdL_2IrIQA"

        val userMessage = "Identify the company by barcode/QR number ${barCodeNumber} and " +
                "find out if this company sustainable and eco-friendly. Answer shortly (Just day if company sustainable or not) " +
                "and don't write the barcode number." +
                "If you cannot identify company just say it."

        val messages = JSONArray()
        messages.put(JSONObject().put("role", "user").put("content", userMessage))


        val json = JSONObject()
        json.put("model", "gpt-4")
        json.put("messages", messages)
        json.put("max_tokens", 100)
        json.put("temperature", 0.7)


        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            json.toString()
        )

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $chatGPTKey")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", "API failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                val jsonResponse = responseData?.let { JSONObject(it) }
                val choicesArray = jsonResponse?.getJSONArray("choices")
                val messageObject = choicesArray?.getJSONObject(0)?.getJSONObject("message")
                val content = messageObject?.getString("content")
                if (content != null) {
                    var textBlock = findViewById<TextView>(R.id.textView2)

//                    textResult.value = content
                    textBlock.text = content
                    Log.d("ResultLog", "Barcode number: $barCodeNumber")
                    Log.d("ResultLog", "Result: $content")
                }
            }
        })
    }

    private val barCodeLauncher = registerForActivityResult(ScanContract())
    {
        result ->
        if (result.contents == null)
        {
            Toast.makeText(this@MainActivity2,
                "Cancelled", Toast.LENGTH_SHORT).show()
        }
        else
        {
            getResponse(result.contents)
        }
    }

    private fun showCamera()
    {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        options.setPrompt("Scan a QR or Barcode")
        options .setCameraId(0)
        options.setBeepEnabled(false)
        options.setOrientationLocked(false)

        barCodeLauncher.launch(options)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    )
    {
            isGraded ->
        if (isGraded)
        {
            showCamera()
        }
    }

    private fun checkCameraPermission(context: Context)
    {
        if (ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED)
        {
            showCamera()
        }
        else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA))
        {
            Toast.makeText(this@MainActivity2,
                "Camera required", Toast.LENGTH_SHORT).show()
        }
        else
        {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding: ActivityMain2Binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        val homeButton = findViewById<Button>(R.id.buttonToMainPage)
        val makeScanButton = findViewById<Button>(R.id.MakeScanButton)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        makeScanButton.setOnClickListener {
            checkCameraPermission(this@MainActivity2)
        }
    }
}


