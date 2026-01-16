package com.example.project1project.security

// Used to define how the key should be generated and stored
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties

// Android system keystore
import java.security.KeyStore

// Used to generate AES keys
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/*
 * KeyStoreManager
 * ----------------
 * Responsible for:
 * 1. Creating AES key securely (only once)
 * 2. Storing it inside Android Keystore
 * 3. Returning the same key for encryption & decryption
 *
 * NOTE:
 * - The key NEVER leaves the keystore
 * - We only get a reference to it
 */
object KeyStoreManager {

    // Name of Android's secure keystore
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"

    // Unique name (alias) for our AES key
    private const val KEY_ALIAS = "CampusPagerAESKey"

    /*
     * Returns AES key if already created
     * Otherwise generates a new one securely
     */
    fun getOrCreateSecretKey(): SecretKey {

        // Access Android Keystore
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)

        // Load keystore (mandatory step)
        keyStore.load(null)

        // 🔁 STEP 1: Check if key already exists
        val existingKey = keyStore.getKey(KEY_ALIAS, null)

        if (existingKey != null) {
            // Key already exists → reuse it
            return existingKey as SecretKey
        }

        // 🔁 STEP 2: Create new AES key if not found
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,   // AES encryption
            ANDROID_KEYSTORE                  // Store inside Android Keystore
        )

        // Define key properties
        val keySpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,                         // Key name
            KeyProperties.PURPOSE_ENCRYPT or   // Can encrypt
                    KeyProperties.PURPOSE_DECRYPT // Can decrypt
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM) // AES-GCM mode
            .setEncryptionPaddings(
                KeyProperties.ENCRYPTION_PADDING_NONE   // No padding for GCM
            )
            .setKeySize(128)                             // AES-128
            .build()

        // Initialize key generator with specs
        keyGenerator.init(keySpec)

        // 🔐 Generate and store key securely
        return keyGenerator.generateKey()
    }
}
