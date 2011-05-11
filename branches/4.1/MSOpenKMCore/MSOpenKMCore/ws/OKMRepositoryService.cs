﻿//------------------------------------------------------------------------------
// <auto-generated>
//     Este código fue generado por una herramienta.
//     Versión del motor en tiempo de ejecución:2.0.50727.1433
//
//     Los cambios en este archivo podrían causar un comportamiento incorrecto y se perderán si
//     se vuelve a generar el código.
// </auto-generated>
//------------------------------------------------------------------------------

using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.Xml.Serialization;

namespace MSOpenKMCore.ws
{

    // 
    // Este código fuente fue generado automáticamente por wsdl, Versión=2.0.50727.1432.
    // 


    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.1432")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Web.Services.WebServiceBindingAttribute(Name = "OKMRepositoryBinding", Namespace = "http://endpoint.ws.openkm.git.es/")]
    public partial class OKMRepositoryService : System.Web.Services.Protocols.SoapHttpClientProtocol
    {

        private System.Threading.SendOrPostCallback getPersonalFolderOperationCompleted;

        private System.Threading.SendOrPostCallback getRootFolderOperationCompleted;

        private System.Threading.SendOrPostCallback getTemplatesFolderOperationCompleted;

        private System.Threading.SendOrPostCallback getTrashFolderOperationCompleted;

        private System.Threading.SendOrPostCallback hasNodeOperationCompleted;

        private System.Threading.SendOrPostCallback purgeTrashOperationCompleted;

        /// <remarks/>
        public OKMRepositoryService(String host)
        {
            if (host.EndsWith("/"))
            {
                this.Url = host + "OKMRepository";
            }
            else
            {
                this.Url = host + "/OKMRepository";
            }
        }

        /// <remarks/>
        public event getPersonalFolderCompletedEventHandler getPersonalFolderCompleted;

        /// <remarks/>
        public event getRootFolderCompletedEventHandler getRootFolderCompleted;

        /// <remarks/>
        public event getTemplatesFolderCompletedEventHandler getTemplatesFolderCompleted;

        /// <remarks/>
        public event getTrashFolderCompletedEventHandler getTrashFolderCompleted;

        /// <remarks/>
        public event hasNodeCompletedEventHandler hasNodeCompleted;

        /// <remarks/>
        public event purgeTrashCompletedEventHandler purgeTrashCompleted;

        /// <remarks/>
        [System.Web.Services.Protocols.SoapRpcMethodAttribute("", RequestNamespace = "http://endpoint.ws.openkm.git.es/", ResponseNamespace = "http://endpoint.ws.openkm.git.es/", Use = System.Web.Services.Description.SoapBindingUse.Literal)]
        [return: System.Xml.Serialization.XmlElementAttribute("return")]
        public folder getPersonalFolder(string arg0)
        {
            object[] results = this.Invoke("getPersonalFolder", new object[] {
                    arg0});
            return ((folder)(results[0]));
        }

        /// <remarks/>
        public System.IAsyncResult BegingetPersonalFolder(string arg0, System.AsyncCallback callback, object asyncState)
        {
            return this.BeginInvoke("getPersonalFolder", new object[] {
                    arg0}, callback, asyncState);
        }

        /// <remarks/>
        public folder EndgetPersonalFolder(System.IAsyncResult asyncResult)
        {
            object[] results = this.EndInvoke(asyncResult);
            return ((folder)(results[0]));
        }

        /// <remarks/>
        public void getPersonalFolderAsync(string arg0)
        {
            this.getPersonalFolderAsync(arg0, null);
        }

        /// <remarks/>
        public void getPersonalFolderAsync(string arg0, object userState)
        {
            if ((this.getPersonalFolderOperationCompleted == null))
            {
                this.getPersonalFolderOperationCompleted = new System.Threading.SendOrPostCallback(this.OngetPersonalFolderOperationCompleted);
            }
            this.InvokeAsync("getPersonalFolder", new object[] {
                    arg0}, this.getPersonalFolderOperationCompleted, userState);
        }

        private void OngetPersonalFolderOperationCompleted(object arg)
        {
            if ((this.getPersonalFolderCompleted != null))
            {
                System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
                this.getPersonalFolderCompleted(this, new getPersonalFolderCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
            }
        }

        /// <remarks/>
        [System.Web.Services.Protocols.SoapRpcMethodAttribute("", RequestNamespace = "http://endpoint.ws.openkm.git.es/", ResponseNamespace = "http://endpoint.ws.openkm.git.es/", Use = System.Web.Services.Description.SoapBindingUse.Literal)]
        [return: System.Xml.Serialization.XmlElementAttribute("return")]
        public folder getRootFolder(string arg0)
        {
            object[] results = this.Invoke("getRootFolder", new object[] {
                    arg0});
            return ((folder)(results[0]));
        }

        /// <remarks/>
        public System.IAsyncResult BegingetRootFolder(string arg0, System.AsyncCallback callback, object asyncState)
        {
            return this.BeginInvoke("getRootFolder", new object[] {
                    arg0}, callback, asyncState);
        }

        /// <remarks/>
        public folder EndgetRootFolder(System.IAsyncResult asyncResult)
        {
            object[] results = this.EndInvoke(asyncResult);
            return ((folder)(results[0]));
        }

        /// <remarks/>
        public void getRootFolderAsync(string arg0)
        {
            this.getRootFolderAsync(arg0, null);
        }

        /// <remarks/>
        public void getRootFolderAsync(string arg0, object userState)
        {
            if ((this.getRootFolderOperationCompleted == null))
            {
                this.getRootFolderOperationCompleted = new System.Threading.SendOrPostCallback(this.OngetRootFolderOperationCompleted);
            }
            this.InvokeAsync("getRootFolder", new object[] {
                    arg0}, this.getRootFolderOperationCompleted, userState);
        }

        private void OngetRootFolderOperationCompleted(object arg)
        {
            if ((this.getRootFolderCompleted != null))
            {
                System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
                this.getRootFolderCompleted(this, new getRootFolderCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
            }
        }

        /// <remarks/>
        [System.Web.Services.Protocols.SoapRpcMethodAttribute("", RequestNamespace = "http://endpoint.ws.openkm.git.es/", ResponseNamespace = "http://endpoint.ws.openkm.git.es/", Use = System.Web.Services.Description.SoapBindingUse.Literal)]
        [return: System.Xml.Serialization.XmlElementAttribute("return")]
        public folder getTemplatesFolder(string arg0)
        {
            object[] results = this.Invoke("getTemplatesFolder", new object[] {
                    arg0});
            return ((folder)(results[0]));
        }

        /// <remarks/>
        public System.IAsyncResult BegingetTemplatesFolder(string arg0, System.AsyncCallback callback, object asyncState)
        {
            return this.BeginInvoke("getTemplatesFolder", new object[] {
                    arg0}, callback, asyncState);
        }

        /// <remarks/>
        public folder EndgetTemplatesFolder(System.IAsyncResult asyncResult)
        {
            object[] results = this.EndInvoke(asyncResult);
            return ((folder)(results[0]));
        }

        /// <remarks/>
        public void getTemplatesFolderAsync(string arg0)
        {
            this.getTemplatesFolderAsync(arg0, null);
        }

        /// <remarks/>
        public void getTemplatesFolderAsync(string arg0, object userState)
        {
            if ((this.getTemplatesFolderOperationCompleted == null))
            {
                this.getTemplatesFolderOperationCompleted = new System.Threading.SendOrPostCallback(this.OngetTemplatesFolderOperationCompleted);
            }
            this.InvokeAsync("getTemplatesFolder", new object[] {
                    arg0}, this.getTemplatesFolderOperationCompleted, userState);
        }

        private void OngetTemplatesFolderOperationCompleted(object arg)
        {
            if ((this.getTemplatesFolderCompleted != null))
            {
                System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
                this.getTemplatesFolderCompleted(this, new getTemplatesFolderCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
            }
        }

        /// <remarks/>
        [System.Web.Services.Protocols.SoapRpcMethodAttribute("", RequestNamespace = "http://endpoint.ws.openkm.git.es/", ResponseNamespace = "http://endpoint.ws.openkm.git.es/", Use = System.Web.Services.Description.SoapBindingUse.Literal)]
        [return: System.Xml.Serialization.XmlElementAttribute("return")]
        public folder getTrashFolder(string arg0)
        {
            object[] results = this.Invoke("getTrashFolder", new object[] {
                    arg0});
            return ((folder)(results[0]));
        }

        /// <remarks/>
        public System.IAsyncResult BegingetTrashFolder(string arg0, System.AsyncCallback callback, object asyncState)
        {
            return this.BeginInvoke("getTrashFolder", new object[] {
                    arg0}, callback, asyncState);
        }

        /// <remarks/>
        public folder EndgetTrashFolder(System.IAsyncResult asyncResult)
        {
            object[] results = this.EndInvoke(asyncResult);
            return ((folder)(results[0]));
        }

        /// <remarks/>
        public void getTrashFolderAsync(string arg0)
        {
            this.getTrashFolderAsync(arg0, null);
        }

        /// <remarks/>
        public void getTrashFolderAsync(string arg0, object userState)
        {
            if ((this.getTrashFolderOperationCompleted == null))
            {
                this.getTrashFolderOperationCompleted = new System.Threading.SendOrPostCallback(this.OngetTrashFolderOperationCompleted);
            }
            this.InvokeAsync("getTrashFolder", new object[] {
                    arg0}, this.getTrashFolderOperationCompleted, userState);
        }

        private void OngetTrashFolderOperationCompleted(object arg)
        {
            if ((this.getTrashFolderCompleted != null))
            {
                System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
                this.getTrashFolderCompleted(this, new getTrashFolderCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
            }
        }

        /// <remarks/>
        [System.Web.Services.Protocols.SoapRpcMethodAttribute("", RequestNamespace = "http://endpoint.ws.openkm.git.es/", ResponseNamespace = "http://endpoint.ws.openkm.git.es/", Use = System.Web.Services.Description.SoapBindingUse.Literal)]
        [return: System.Xml.Serialization.XmlElementAttribute("return")]
        public bool hasNode(string arg0, string arg1)
        {
            object[] results = this.Invoke("hasNode", new object[] {
                    arg0,
                    arg1});
            return ((bool)(results[0]));
        }

        /// <remarks/>
        public System.IAsyncResult BeginhasNode(string arg0, string arg1, System.AsyncCallback callback, object asyncState)
        {
            return this.BeginInvoke("hasNode", new object[] {
                    arg0,
                    arg1}, callback, asyncState);
        }

        /// <remarks/>
        public bool EndhasNode(System.IAsyncResult asyncResult)
        {
            object[] results = this.EndInvoke(asyncResult);
            return ((bool)(results[0]));
        }

        /// <remarks/>
        public void hasNodeAsync(string arg0, string arg1)
        {
            this.hasNodeAsync(arg0, arg1, null);
        }

        /// <remarks/>
        public void hasNodeAsync(string arg0, string arg1, object userState)
        {
            if ((this.hasNodeOperationCompleted == null))
            {
                this.hasNodeOperationCompleted = new System.Threading.SendOrPostCallback(this.OnhasNodeOperationCompleted);
            }
            this.InvokeAsync("hasNode", new object[] {
                    arg0,
                    arg1}, this.hasNodeOperationCompleted, userState);
        }

        private void OnhasNodeOperationCompleted(object arg)
        {
            if ((this.hasNodeCompleted != null))
            {
                System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
                this.hasNodeCompleted(this, new hasNodeCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
            }
        }

        /// <remarks/>
        [System.Web.Services.Protocols.SoapRpcMethodAttribute("", RequestNamespace = "http://endpoint.ws.openkm.git.es/", ResponseNamespace = "http://endpoint.ws.openkm.git.es/", Use = System.Web.Services.Description.SoapBindingUse.Literal)]
        public void purgeTrash(string arg0)
        {
            this.Invoke("purgeTrash", new object[] {
                    arg0});
        }

        /// <remarks/>
        public System.IAsyncResult BeginpurgeTrash(string arg0, System.AsyncCallback callback, object asyncState)
        {
            return this.BeginInvoke("purgeTrash", new object[] {
                    arg0}, callback, asyncState);
        }

        /// <remarks/>
        public void EndpurgeTrash(System.IAsyncResult asyncResult)
        {
            this.EndInvoke(asyncResult);
        }

        /// <remarks/>
        public void purgeTrashAsync(string arg0)
        {
            this.purgeTrashAsync(arg0, null);
        }

        /// <remarks/>
        public void purgeTrashAsync(string arg0, object userState)
        {
            if ((this.purgeTrashOperationCompleted == null))
            {
                this.purgeTrashOperationCompleted = new System.Threading.SendOrPostCallback(this.OnpurgeTrashOperationCompleted);
            }
            this.InvokeAsync("purgeTrash", new object[] {
                    arg0}, this.purgeTrashOperationCompleted, userState);
        }

        private void OnpurgeTrashOperationCompleted(object arg)
        {
            if ((this.purgeTrashCompleted != null))
            {
                System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
                this.purgeTrashCompleted(this, new System.ComponentModel.AsyncCompletedEventArgs(invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
            }
        }

        /// <remarks/>
        public new void CancelAsync(object userState)
        {
            base.CancelAsync(userState);
        }
    }

    /// <comentarios/>

    public partial class folder
    {


    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.1432")]
    public delegate void getPersonalFolderCompletedEventHandler(object sender, getPersonalFolderCompletedEventArgs e);

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.1432")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public partial class getPersonalFolderCompletedEventArgs : System.ComponentModel.AsyncCompletedEventArgs
    {

        private object[] results;

        internal getPersonalFolderCompletedEventArgs(object[] results, System.Exception exception, bool cancelled, object userState) :
            base(exception, cancelled, userState)
        {
            this.results = results;
        }

        /// <remarks/>
        public folder Result
        {
            get
            {
                this.RaiseExceptionIfNecessary();
                return ((folder)(this.results[0]));
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.1432")]
    public delegate void getRootFolderCompletedEventHandler(object sender, getRootFolderCompletedEventArgs e);

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.1432")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public partial class getRootFolderCompletedEventArgs : System.ComponentModel.AsyncCompletedEventArgs
    {

        private object[] results;

        internal getRootFolderCompletedEventArgs(object[] results, System.Exception exception, bool cancelled, object userState) :
            base(exception, cancelled, userState)
        {
            this.results = results;
        }

        /// <remarks/>
        public folder Result
        {
            get
            {
                this.RaiseExceptionIfNecessary();
                return ((folder)(this.results[0]));
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.1432")]
    public delegate void getTemplatesFolderCompletedEventHandler(object sender, getTemplatesFolderCompletedEventArgs e);

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.1432")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public partial class getTemplatesFolderCompletedEventArgs : System.ComponentModel.AsyncCompletedEventArgs
    {

        private object[] results;

        internal getTemplatesFolderCompletedEventArgs(object[] results, System.Exception exception, bool cancelled, object userState) :
            base(exception, cancelled, userState)
        {
            this.results = results;
        }

        /// <remarks/>
        public folder Result
        {
            get
            {
                this.RaiseExceptionIfNecessary();
                return ((folder)(this.results[0]));
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.1432")]
    public delegate void getTrashFolderCompletedEventHandler(object sender, getTrashFolderCompletedEventArgs e);

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.1432")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public partial class getTrashFolderCompletedEventArgs : System.ComponentModel.AsyncCompletedEventArgs
    {

        private object[] results;

        internal getTrashFolderCompletedEventArgs(object[] results, System.Exception exception, bool cancelled, object userState) :
            base(exception, cancelled, userState)
        {
            this.results = results;
        }

        /// <remarks/>
        public folder Result
        {
            get
            {
                this.RaiseExceptionIfNecessary();
                return ((folder)(this.results[0]));
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.1432")]
    public delegate void hasNodeCompletedEventHandler(object sender, hasNodeCompletedEventArgs e);

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.1432")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public partial class hasNodeCompletedEventArgs : System.ComponentModel.AsyncCompletedEventArgs
    {

        private object[] results;

        internal hasNodeCompletedEventArgs(object[] results, System.Exception exception, bool cancelled, object userState) :
            base(exception, cancelled, userState)
        {
            this.results = results;
        }

        /// <remarks/>
        public bool Result
        {
            get
            {
                this.RaiseExceptionIfNecessary();
                return ((bool)(this.results[0]));
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.1432")]
    public delegate void purgeTrashCompletedEventHandler(object sender, System.ComponentModel.AsyncCompletedEventArgs e);

}