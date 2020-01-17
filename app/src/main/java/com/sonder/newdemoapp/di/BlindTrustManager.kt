package com.sonder.newdemoapp.di

import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

class BlindTrustManager : X509TrustManager {
    override fun getAcceptedIssuers(): Array<X509Certificate>? {
        return arrayOf()
    }


    @Throws(java.security.cert.CertificateException::class)
    override fun checkClientTrusted(
        chain: Array<X509Certificate>,
        authType: String
    ) {
    }

    @Throws(java.security.cert.CertificateException::class)
    override fun checkServerTrusted(
        chain: Array<X509Certificate>,
        authType: String
    ) {
    }
}