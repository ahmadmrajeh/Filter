package com.example.datascource.utils
import android.content.Context
import android.util.Log
import java.io.IOException


class JsonMockApi {

   companion object {
       fun getJsonDataFromAsset(context: Context, fileName: String): String? {
           val jsonString: String
           try {
               jsonString = context.assets.open(fileName).bufferedReader()
                   .use { it.readText()
               }
           } catch (ioException: IOException) {
               ioException.printStackTrace()
               Log.e("jsonErorr","try Failed "+ioException.message)
               Log.e("jsonErorr","  Failed cause"+ioException.cause)
               return null

           }

           return jsonString
       }

   }


}