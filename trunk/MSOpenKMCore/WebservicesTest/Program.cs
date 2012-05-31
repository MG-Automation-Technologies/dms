using System;
using System.Collections.Generic;
using System.Text;
using MSOpenKMCore.ws;
using System.Security.Cryptography.X509Certificates;
using System.Net;
using System.Net.Security;

namespace WebservicesTest
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                ServicePointManager.ServerCertificateValidationCallback += new RemoteCertificateValidationCallback(ValidateServerCertificate);
                OKMAuthService authService = new OKMAuthService("http://demo.openkm.com/OpenKM/");
                //X509Certificate2 cert = GetCert("openkm.com");
                //authService.ClientCertificates.Add(cert);


                
                String token = token = authService.login("user1", "pass1");
                authService.logout(token);
            }
            catch (Exception e)
            {
                String errorMsg = "Error\n" + e.Message;
                System.Diagnostics.Debug.Write(errorMsg);
            }
        }

        public static bool ValidateServerCertificate(Object sender, X509Certificate certificate, X509Chain chain, SslPolicyErrors sslPolicyErrors)
        {
            return true;
        }

        /* public static X509Certificate2 GetCert(string certificateSubjectName)
        {
            var storeMy = new X509Store(StoreName.My, StoreLocation.CurrentUser);
            storeMy.Open(OpenFlags.ReadOnly);
            X509Certificate2Collection certColl =
              storeMy.Certificates.Find(X509FindType.FindBySubjectName,
              certificateSubjectName, true);
            storeMy.Close();
            if (certColl.Count != 0)
            {
                return certColl[0];
            }
            return null;
        } */
    }
}
