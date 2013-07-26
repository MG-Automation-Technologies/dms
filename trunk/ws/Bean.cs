using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MSOpenKMCore.ws
{
    /// <comentarios/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.3038")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://endpoint.ws.openkm.com/")]
    public partial class version
    {

        private bool actualField;

        private string authorField;

        private string commentField;

        private System.DateTime createdField;

        private bool createdFieldSpecified;

        private string nameField;

        private long sizeField;

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public bool actual
        {
            get
            {
                return this.actualField;
            }
            set
            {
                this.actualField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string author
        {
            get
            {
                return this.authorField;
            }
            set
            {
                this.authorField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string comment
        {
            get
            {
                return this.commentField;
            }
            set
            {
                this.commentField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public System.DateTime created
        {
            get
            {
                return this.createdField;
            }
            set
            {
                this.createdField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool createdSpecified
        {
            get
            {
                return this.createdFieldSpecified;
            }
            set
            {
                this.createdFieldSpecified = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string name
        {
            get
            {
                return this.nameField;
            }
            set
            {
                this.nameField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public long size
        {
            get
            {
                return this.sizeField;
            }
            set
            {
                this.sizeField = value;
            }
        }
    }

    /// <comentarios/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.3038")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://endpoint.ws.openkm.com/")]
    public partial class note
    {

        private System.DateTime dateField;

        private bool dateFieldSpecified;

        private string pathField;

        private string textField;

        private string userField;

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public System.DateTime date
        {
            get
            {
                return this.dateField;
            }
            set
            {
                this.dateField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool dateSpecified
        {
            get
            {
                return this.dateFieldSpecified;
            }
            set
            {
                this.dateFieldSpecified = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string path
        {
            get
            {
                return this.pathField;
            }
            set
            {
                this.pathField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string user
        {
            get
            {
                return this.userField;
            }
            set
            {
                this.userField = value;
            }
        }
    }

    /// <comentarios/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.3038")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://endpoint.ws.openkm.com/")]
    public partial class @lock
    {

        private string nodePathField;

        private string ownerField;

        private string tokenField;

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string nodePath
        {
            get
            {
                return this.nodePathField;
            }
            set
            {
                this.nodePathField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string owner
        {
            get
            {
                return this.ownerField;
            }
            set
            {
                this.ownerField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string token
        {
            get
            {
                return this.tokenField;
            }
            set
            {
                this.tokenField = value;
            }
        }
    }

    /// <comentarios/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.3038")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://endpoint.ws.openkm.com/")]
    public partial class folder
    {

        private string authorField;

        private System.DateTime createdField;

        private bool createdFieldSpecified;

        private bool hasChildsField;

        private string pathField;

        private sbyte permissionsField;

        private bool subscribedField;

        private string[] subscriptorsField;

        private string uuidField;

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string author
        {
            get
            {
                return this.authorField;
            }
            set
            {
                this.authorField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public System.DateTime created
        {
            get
            {
                return this.createdField;
            }
            set
            {
                this.createdField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool createdSpecified
        {
            get
            {
                return this.createdFieldSpecified;
            }
            set
            {
                this.createdFieldSpecified = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public bool hasChilds
        {
            get
            {
                return this.hasChildsField;
            }
            set
            {
                this.hasChildsField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string path
        {
            get
            {
                return this.pathField;
            }
            set
            {
                this.pathField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public sbyte permissions
        {
            get
            {
                return this.permissionsField;
            }
            set
            {
                this.permissionsField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public bool subscribed
        {
            get
            {
                return this.subscribedField;
            }
            set
            {
                this.subscribedField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute("subscriptors", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true)]
        public string[] subscriptors
        {
            get
            {
                return this.subscriptorsField;
            }
            set
            {
                this.subscriptorsField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string uuid
        {
            get
            {
                return this.uuidField;
            }
            set
            {
                this.uuidField = value;
            }
        }
    }

    /// <comentarios/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.3038")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://endpoint.ws.openkm.com/")]
    public partial class document
    {

        private version actualVersionField;

        private string authorField;

        private folder[] categoriesField;

        private bool checkedOutField;

        private bool convertibleToPdfField;

        private bool convertibleToSwfField;

        private System.DateTime createdField;

        private bool createdFieldSpecified;

        private string[] keywordsField;

        private string languageField;

        private System.DateTime lastModifiedField;

        private bool lastModifiedFieldSpecified;

        private @lock lockInfoField;

        private bool lockedField;

        private string mimeTypeField;

        private note[] notesField;

        private string pathField;

        private sbyte permissionsField;

        private bool subscribedField;

        private string[] subscriptorsField;

        private string uuidField;

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public version actualVersion
        {
            get
            {
                return this.actualVersionField;
            }
            set
            {
                this.actualVersionField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string author
        {
            get
            {
                return this.authorField;
            }
            set
            {
                this.authorField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute("categories", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true)]
        public folder[] categories
        {
            get
            {
                return this.categoriesField;
            }
            set
            {
                this.categoriesField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public bool checkedOut
        {
            get
            {
                return this.checkedOutField;
            }
            set
            {
                this.checkedOutField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public bool convertibleToPdf
        {
            get
            {
                return this.convertibleToPdfField;
            }
            set
            {
                this.convertibleToPdfField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public bool convertibleToSwf
        {
            get
            {
                return this.convertibleToSwfField;
            }
            set
            {
                this.convertibleToSwfField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public System.DateTime created
        {
            get
            {
                return this.createdField;
            }
            set
            {
                this.createdField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool createdSpecified
        {
            get
            {
                return this.createdFieldSpecified;
            }
            set
            {
                this.createdFieldSpecified = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute("keywords", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true)]
        public string[] keywords
        {
            get
            {
                return this.keywordsField;
            }
            set
            {
                this.keywordsField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string language
        {
            get
            {
                return this.languageField;
            }
            set
            {
                this.languageField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public System.DateTime lastModified
        {
            get
            {
                return this.lastModifiedField;
            }
            set
            {
                this.lastModifiedField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool lastModifiedSpecified
        {
            get
            {
                return this.lastModifiedFieldSpecified;
            }
            set
            {
                this.lastModifiedFieldSpecified = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public @lock lockInfo
        {
            get
            {
                return this.lockInfoField;
            }
            set
            {
                this.lockInfoField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public bool locked
        {
            get
            {
                return this.lockedField;
            }
            set
            {
                this.lockedField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string mimeType
        {
            get
            {
                return this.mimeTypeField;
            }
            set
            {
                this.mimeTypeField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute("notes", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true)]
        public note[] notes
        {
            get
            {
                return this.notesField;
            }
            set
            {
                this.notesField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string path
        {
            get
            {
                return this.pathField;
            }
            set
            {
                this.pathField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public sbyte permissions
        {
            get
            {
                return this.permissionsField;
            }
            set
            {
                this.permissionsField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public bool subscribed
        {
            get
            {
                return this.subscribedField;
            }
            set
            {
                this.subscribedField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute("subscriptors", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true)]
        public string[] subscriptors
        {
            get
            {
                return this.subscriptorsField;
            }
            set
            {
                this.subscriptorsField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string uuid
        {
            get
            {
                return this.uuidField;
            }
            set
            {
                this.uuidField = value;
            }
        }
    }

    /// <comentarios/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "2.0.50727.3038")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://endpoint.ws.openkm.com/")]
    public partial class mail
    {

        private document[] attachmentsField;

        private string[] bccField;

        private string[] ccField;

        private string contentField;

        private string fromField;

        private string mimeTypeField;

        private string pathField;

        private sbyte permissionsField;

        private System.DateTime receivedDateField;

        private bool receivedDateFieldSpecified;

        private string[] replyField;

        private System.DateTime sentDateField;

        private bool sentDateFieldSpecified;

        private long sizeField;

        private string subjectField;

        private string[] toField;

        private string uuidField;

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute("attachments", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true)]
        public document[] attachments
        {
            get
            {
                return this.attachmentsField;
            }
            set
            {
                this.attachmentsField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute("bcc", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true)]
        public string[] bcc
        {
            get
            {
                return this.bccField;
            }
            set
            {
                this.bccField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute("cc", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true)]
        public string[] cc
        {
            get
            {
                return this.ccField;
            }
            set
            {
                this.ccField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string content
        {
            get
            {
                return this.contentField;
            }
            set
            {
                this.contentField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string from
        {
            get
            {
                return this.fromField;
            }
            set
            {
                this.fromField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string mimeType
        {
            get
            {
                return this.mimeTypeField;
            }
            set
            {
                this.mimeTypeField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string path
        {
            get
            {
                return this.pathField;
            }
            set
            {
                this.pathField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public sbyte permissions
        {
            get
            {
                return this.permissionsField;
            }
            set
            {
                this.permissionsField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public System.DateTime receivedDate
        {
            get
            {
                return this.receivedDateField;
            }
            set
            {
                this.receivedDateField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool receivedDateSpecified
        {
            get
            {
                return this.receivedDateFieldSpecified;
            }
            set
            {
                this.receivedDateFieldSpecified = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute("reply", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true)]
        public string[] reply
        {
            get
            {
                return this.replyField;
            }
            set
            {
                this.replyField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public System.DateTime sentDate
        {
            get
            {
                return this.sentDateField;
            }
            set
            {
                this.sentDateField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool sentDateSpecified
        {
            get
            {
                return this.sentDateFieldSpecified;
            }
            set
            {
                this.sentDateFieldSpecified = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public long size
        {
            get
            {
                return this.sizeField;
            }
            set
            {
                this.sizeField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string subject
        {
            get
            {
                return this.subjectField;
            }
            set
            {
                this.subjectField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute("to", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true)]
        public string[] to
        {
            get
            {
                return this.toField;
            }
            set
            {
                this.toField = value;
            }
        }

        /// <comentarios/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string uuid
        {
            get
            {
                return this.uuidField;
            }
            set
            {
                this.uuidField = value;
            }
        }
    }
}
