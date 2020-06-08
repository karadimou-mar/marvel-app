package com.example.marvelsquadhero.utils

class MarvelRequestGenerator private constructor(){

    companion object{
        fun createParams(): MarvelRequestGenerator{
            val generator = MarvelRequestGenerator()
            generator.setRequestParams()
            return generator
        }
    }

    val apiKey: String = com.example.marvelsquadhero.BuildConfig.PUBLIC_KEY
    val privateKey: String = com.example.marvelsquadhero.BuildConfig.PRIVATE_KEY
    var timestamp :Long? = null
    var hash: String? = null

    private fun setRequestParams(){
        timestamp = System.currentTimeMillis()
        val currentHash: String = timestamp.toString() + privateKey + apiKey
        hash = currentHash.toMD5()
    }

}