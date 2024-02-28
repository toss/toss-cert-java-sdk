package im.toss.cert.sdk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PKCS7CertificateExtractorTest {

    @Test
    void test() {
        String pemCertificate = PKCS7CertificateExtractor.extractCertificate(pkcs7Data);
        assertEquals(expectedPemCertificate, pemCertificate);
    }

    private final String pkcs7Data =
        "MIIIZAYJKoZIhvcNAQcCoIIIVTCCCFECAQExDzANBglghkgBZQMEAgEFADAeBgkqhkiG9w0BBwGg" +
            "EQQPZG9jdW1lbnQgc2FtcGxloIIGJjCCBiIwggQKoAMCAQICBAFAvqIwDQYJKoZIhvcNAQELBQAw" +
            "UTELMAkGA1UEBgwCS1IxGzAZBgNVBAoMElZpdmEgUmVwdWJsaWNhIEluYzESMBAGA1UECwwJVG9z" +
            "cyBDZXJ0MREwDwYDVQQDDAhUb3NzIENBMTAeFw0yNDAyMTUwNTIzNDdaFw0yNzAyMTQxNDU5NTla" +
            "MHwxCzAJBgNVBAYTAktSMRswGQYDVQQKDBJWaXZhIFJlcHVibGljYSBJbmMxEjAQBgNVBAsMCVRv" +
            "c3MgQ2VydDEoMCYGCgmSJomT8ixkAQEMGDcwMDI3MjEyMDIxMTExODgwMjAwMDAwMTESMBAGA1UE" +
            "AwwJ7KCV7J246raMMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkx0j1LpuVyn0G8RH" +
            "oXleMrB27nf4uH57lr1Bchgkp5CysakjIQ/Q06Gosr4JmlrfPYiAlPHYcqTwKvU/uxUK7GoH9Ymx" +
            "K8Z4xFJuHAQdMzMS63QVq5LH5VFjujutpR0vY5rQOSaQZUTSuybSlI9GxCUJwh0bSZhPUmHrpURl" +
            "Qj//fGk8+LpBDsXUECypGq7UlM5M4z1bOOYA1I3SyCmvnqIiMd4xNx+Vy1bRvB3U6fE/vsplYFvz" +
            "Efrcc2xVfnPGypskQZ0iv0ydw6TegAMkOGUttZQik7wMyZ7in4lbpidpTdyzGpoQtbUm3A56tbWG" +
            "N4y9ru0RvtbvJVsTftULVQIDAQABo4IB1TCCAdEwfgYDVR0jBHcwdYAUIOEEYoA6EFhC3FSBskx+" +
            "jPX3qh+hWqRYMFYxCzAJBgNVBAYMAktSMRswGQYDVQQKDBJWaXZhIFJlcHVibGljYSBJbmMxEjAQ" +
            "BgNVBAsMCVRvc3MgQ2VydDEWMBQGA1UEAwwNVG9zcyBSb290IENBMYIBAjAdBgNVHQ4EFgQUGGl2" +
            "Nn0TMLprdD5gjlLyzrJPOgUwDgYDVR0PAQH/BAQDAgbAMIGLBgNVHSABAf8EgYAwfjB8BgsqgxqM" +
            "myIFAQEBAzBtMCsGCCsGAQUFBwIBFh9odHRwOi8vY2EuY2VydC50b3NzLmltL2Nwcy5odG1sMD4G" +
            "CCsGAQUFBwICMDIeMABUAGgAaQBzACAAaQBzACAAVABvAHMAcwAgAGMAZQByAHQAaQBmAGkAYwBh" +
            "AHQAZTBbBgNVHR8EVDBSMFCgTqBMhkpodHRwOi8vY2EuY2VydC50b3NzLmltL2NybC90b3NzX2Ny" +
            "bF9kcDJwNzAwNy5jcmw/Y2VydGlmaWNhdGVSZXZvY2F0aW9uTGlzdDA1BggrBgEFBQcBAQQpMCcw" +
            "JQYIKwYBBQUHMAGGGWh0dHA6Ly9vY3NwLmNlcnQudG9zcy5pbS8wDQYJKoZIhvcNAQELBQADggIB" +
            "AC2CSrLGoNRT9KtjA5iGGSD8oOuc7gKcxn19B9gE2fcfe8tl3CiUYCBs6GTO8U29q0CeDVadmN0m" +
            "dOE1V7qMDCdjEQLf4cskMKn0LNj406b+LhsYJPks+9Ra4RAl+P5QMrkVnfKS9HmM+k1snvMJXMV+" +
            "zHwapai0C2cK1dKAttL+WZH5VhP2P48yDCRmoaB8ZzQ1YhS+PVyqtvGqSJBYbJusBXulMULcxbFq" +
            "d/bSbIuyKEvUsd+6NbhT5jKs8EFbZ/fUPRMZusJj/VepIhwyb4hK3QiTgAq88Z8Tcofzq/1oV4Mm" +
            "dRcj7IGdcSd2k6JgP+R6UObNaSTjjDLC9JfiOtXbnz9Lh55fG48r11qP2oZukyJGFdjI6HSiYScA" +
            "cloc2INMFzifWioY3rG/bucLBqJGs5CU3ZYNaTKsGMnU7GPGCm3Rvh14QYB3I918VT8nrlcdiWZl" +
            "MiPj6QUNSdTDuTgUDr/ud50VaD0ZhRNWouZ9w7V+Be+gGpv08HR6Ebdy+MuecxjjpIwPsbeBwjfg" +
            "KPB+aEMdgYfVb8t/TQimbH8vxEUlct8+NRVawLe5F4ZXRMtr8WfbhTPU3aJiraemy7OGXxDkdRkO" +
            "DHhG82Gjl8AQ4Rs5wveJvnT3a0/UUwBb3XTvYylGCHbw8i+ShbZ0ZU9E6anycByWlBZ16Agq4seR" +
            "MYIB7zCCAesCAQEwWTBRMQswCQYDVQQGDAJLUjEbMBkGA1UECgwSVml2YSBSZXB1YmxpY2EgSW5j" +
            "MRIwEAYDVQQLDAlUb3NzIENlcnQxETAPBgNVBAMMCFRvc3MgQ0ExAgQBQL6iMA0GCWCGSAFlAwQC" +
            "AQUAoGkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMjQwMjI2MTIy" +
            "MTQzWjAvBgkqhkiG9w0BCQQxIgQgtFv6KxZyYKTYL0BTDjk64Y7QBNMHVQwwXxAOO9HsDV4wDQYJ" +
            "KoZIhvcNAQEBBQAEggEAhnqCF4z8tKfszHoBL57oiN4+Odn1rN8ZOUdVnrlFbKG9NbQgysAx9otF" +
            "tF5PGtICTOaRZmPe6VQjxE7QbJv2GGuQX3ezVolM0I3RsCrDkIVycgopCvjZlITq36gnZSCiyAsz" +
            "lVET5mLOQQpsvUWCPaTUjeYghRqE8ItwNtuQoKwy78RLLdQQvF7Q+mJf3H9GSKPzOGl51Hrnc5jx" +
            "ZVJ+1fNGqjOdgggyHiUvFtap8Jja6tEQtt6LGkPdCnNprJjTHOWu7yzn5jmACWUvyY+BgbCF0vJL" +
            "jiJTlKe5W1NK8G3jBJe9pLDQlyFbsScBYr1AqQx+ChusfcQ+c9COGGFbKg==";

    private final String expectedPemCertificate =
        "-----BEGIN CERTIFICATE-----\n" +
            "MIIGIjCCBAqgAwIBAgIEAUC+ojANBgkqhkiG9w0BAQsFADBRMQswCQYDVQQGDAJLUjEbMBkGA1UE" +
            "CgwSVml2YSBSZXB1YmxpY2EgSW5jMRIwEAYDVQQLDAlUb3NzIENlcnQxETAPBgNVBAMMCFRvc3Mg" +
            "Q0ExMB4XDTI0MDIxNTA1MjM0N1oXDTI3MDIxNDE0NTk1OVowfDELMAkGA1UEBhMCS1IxGzAZBgNV" +
            "BAoMElZpdmEgUmVwdWJsaWNhIEluYzESMBAGA1UECwwJVG9zcyBDZXJ0MSgwJgYKCZImiZPyLGQB" +
            "AQwYNzAwMjcyMTIwMjExMTE4ODAyMDAwMDAxMRIwEAYDVQQDDAnsoJXsnbjqtowwggEiMA0GCSqG" +
            "SIb3DQEBAQUAA4IBDwAwggEKAoIBAQCTHSPUum5XKfQbxEeheV4ysHbud/i4fnuWvUFyGCSnkLKx" +
            "qSMhD9DToaiyvgmaWt89iICU8dhypPAq9T+7FQrsagf1ibErxnjEUm4cBB0zMxLrdBWrksflUWO6" +
            "O62lHS9jmtA5JpBlRNK7JtKUj0bEJQnCHRtJmE9SYeulRGVCP/98aTz4ukEOxdQQLKkartSUzkzj" +
            "PVs45gDUjdLIKa+eoiIx3jE3H5XLVtG8HdTp8T++ymVgW/MR+txzbFV+c8bKmyRBnSK/TJ3DpN6A" +
            "AyQ4ZS21lCKTvAzJnuKfiVumJ2lN3LMamhC1tSbcDnq1tYY3jL2u7RG+1u8lWxN+1QtVAgMBAAGj" +
            "ggHVMIIB0TB+BgNVHSMEdzB1gBQg4QRigDoQWELcVIGyTH6M9feqH6FapFgwVjELMAkGA1UEBgwC" +
            "S1IxGzAZBgNVBAoMElZpdmEgUmVwdWJsaWNhIEluYzESMBAGA1UECwwJVG9zcyBDZXJ0MRYwFAYD" +
            "VQQDDA1Ub3NzIFJvb3QgQ0ExggECMB0GA1UdDgQWBBQYaXY2fRMwumt0PmCOUvLOsk86BTAOBgNV" +
            "HQ8BAf8EBAMCBsAwgYsGA1UdIAEB/wSBgDB+MHwGCyqDGoybIgUBAQEDMG0wKwYIKwYBBQUHAgEW" +
            "H2h0dHA6Ly9jYS5jZXJ0LnRvc3MuaW0vY3BzLmh0bWwwPgYIKwYBBQUHAgIwMh4wAFQAaABpAHMA" +
            "IABpAHMAIABUAG8AcwBzACAAYwBlAHIAdABpAGYAaQBjAGEAdABlMFsGA1UdHwRUMFIwUKBOoEyG" +
            "Smh0dHA6Ly9jYS5jZXJ0LnRvc3MuaW0vY3JsL3Rvc3NfY3JsX2RwMnA3MDA3LmNybD9jZXJ0aWZp" +
            "Y2F0ZVJldm9jYXRpb25MaXN0MDUGCCsGAQUFBwEBBCkwJzAlBggrBgEFBQcwAYYZaHR0cDovL29j" +
            "c3AuY2VydC50b3NzLmltLzANBgkqhkiG9w0BAQsFAAOCAgEALYJKssag1FP0q2MDmIYZIPyg65zu" +
            "ApzGfX0H2ATZ9x97y2XcKJRgIGzoZM7xTb2rQJ4NVp2Y3SZ04TVXuowMJ2MRAt/hyyQwqfQs2PjT" +
            "pv4uGxgk+Sz71FrhECX4/lAyuRWd8pL0eYz6TWye8wlcxX7MfBqlqLQLZwrV0oC20v5ZkflWE/Y/" +
            "jzIMJGahoHxnNDViFL49XKq28apIkFhsm6wFe6UxQtzFsWp39tJsi7IoS9Sx37o1uFPmMqzwQVtn" +
            "99Q9Exm6wmP9V6kiHDJviErdCJOACrzxnxNyh/Or/WhXgyZ1FyPsgZ1xJ3aTomA/5HpQ5s1pJOOM" +
            "MsL0l+I61dufP0uHnl8bjyvXWo/ahm6TIkYV2MjodKJhJwByWhzYg0wXOJ9aKhjesb9u5wsGokaz" +
            "kJTdlg1pMqwYydTsY8YKbdG+HXhBgHcj3XxVPyeuVx2JZmUyI+PpBQ1J1MO5OBQOv+53nRVoPRmF" +
            "E1ai5n3DtX4F76Aam/TwdHoRt3L4y55zGOOkjA+xt4HCN+Ao8H5oQx2Bh9Vvy39NCKZsfy/ERSVy" +
            "3z41FVrAt7kXhldEy2vxZ9uFM9TdomKtp6bLs4ZfEOR1GQ4MeEbzYaOXwBDhGznC94m+dPdrT9RT" +
            "AFvddO9jKUYIdvDyL5KFtnRlT0TpqfJwHJaUFnXoCCrix5E=" +
            "\n-----END CERTIFICATE-----";
}
