package com.example.project1project.security

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

/*
 *CryptoUtils
 * ------
 * Handles AES encrytion & decryption
 * Ued for End -to -End Encryption(E2EE)
 *
 * NOTE:
 * ~AES/ECB is used only for hackathon demo
 * ~Production apps should use ASE/GCM WITH IV
 *
 */

object CryptoUtils{
    //16-byte secret key (AES - 128)
    private const val SECRET_KEY = "CampusPagerKey16"
    private const val TRANSFORMATION="AES/GCM/NoPadding"
    private const val IV_SIZE=12               //rECOMMENDED FOR GCM
    private const val TAG_SIZE=128            //AUTHENTICATION TAG SIZE

    /*
        *Encrypts plain text into Base64 encoded cipher string
    */

    fun encrypt(text:String):String{
        val cipher=Cipher.getInstance(TRANSFORMATION)

        val iv=ByteArray(IV_SIZE)
        SecureRandom().nextBytes(iv)

        val keySpec=SecretKeySpec(
            SECRET_KEY.toByteArray(),
            "AES"
        )

        val gcmSpec= GCMParameterSpec(TAG_SIZE,iv)
        cipher.init(Cipher.ENCRYPT_MODE,keySpec,gcmSpec)

        val cipherText=cipher.doFinal(text.toByteArray())

        //Imp prepend iv to ciphertext
        val combined =iv+cipherText


        //convert encrypted bytes -> readable string
        return Base64.encodeToString(combined,Base64.DEFAULT)
    }
    /*
        *DESCRYPTS BASE64 ENCRYPED STRING INTO ORIGINAL TEXT
     */
    fun decrypt(encryptedText: String):String{
        return try{
            val decoded=Base64.decode(encryptedText,Base64.DEFAULT)

            //EXTRACT IV AND CIPHERTEXT
            val iv=decoded.copyOfRange(0,IV_SIZE)
            val cipherText=decoded.copyOfRange(IV_SIZE,decoded.size)

            val cipher=Cipher.getInstance(TRANSFORMATION)

            val keySpec= SecretKeySpec(
                SECRET_KEY.toByteArray(),
                "AES"
            )

            val gcmSpec= GCMParameterSpec(TAG_SIZE,iv)
            cipher.init(Cipher.DECRYPT_MODE,keySpec,gcmSpec)


            String(cipher.doFinal(cipherText))
        }catch(e: Exception){
            //if text is not encrypted ,return as - is
            encryptedText
        }

    }
}



