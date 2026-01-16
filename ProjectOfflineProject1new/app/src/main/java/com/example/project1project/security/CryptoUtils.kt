package com.example.project1project.security

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

/*
 *CryptoUtils
 * ------
// Handles AES encryption & decryption
// Used for local encrypted payloads (not cross-device E2EE)

 *
 * NOTE:
 * ~Uses AES/GCM/NoPadding with Android KeyStore
 * ~AES/ECB is insecure and Must NOt be used
 *
 */

object CryptoUtils{
    private const val TRANSFORMATION="AES/GCM/NoPadding"
    //private const val IV_SIZE=12               //rECOMMENDED FOR GCM
    private const val TAG_SIZE=128            //AUTHENTICATION TAG SIZE

    /*
        *Encrypts plain text into Base64 encoded cipher string
    */

    fun encrypt(text:String):String{
        require(text.isNotBlank()){"Message cannto be empty"}
        val cipher=Cipher.getInstance(TRANSFORMATION)


        // get AES key security from KeyStore
        val secretKey= KeyStoreManager.getOrCreateSecretKey()


//       val iv=ByteArray(IV_SIZE)
//        SecureRandom().nextBytes(iv)

        // 🔐 IMPORTANT: DO NOT PROVIDE IV — let Keystore generate it
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val iv = cipher.iv              // Keystore-generated IV
        val cipherText = cipher.doFinal(text.toByteArray(Charsets.UTF_8))


        // Store IV length + IV + ciphertext
        val combined = ByteArray(1 + iv.size + cipherText.size)
        combined[0] = iv.size.toByte()
        System.arraycopy(iv, 0, combined, 1, iv.size)
        System.arraycopy(cipherText, 0, combined, 1 + iv.size, cipherText.size)

        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }
    /*
        *DESCRYPTS BASE64 ENCRYPED STRING INTO ORIGINAL TEXT
     */
    fun decrypt(encryptedText: String):String{
        return try{
            val decoded =Base64.decode(encryptedText,Base64.NO_WRAP)

            val ivSize=decoded[0].toInt()
            val iv=decoded.copyOfRange(1,1+ivSize)
            val cipherText=decoded.copyOfRange(1+ivSize,decoded.size)

            val cipher=Cipher.getInstance(TRANSFORMATION)
            val secretKey=KeyStoreManager.getOrCreateSecretKey()

            val spec=GCMParameterSpec(TAG_SIZE,iv)
            cipher.init(Cipher.DECRYPT_MODE,secretKey,spec)
            String(cipher.doFinal(cipherText),Charsets.UTF_8)
        }catch(e:Exception){
            encryptedText
        }
    }
}



