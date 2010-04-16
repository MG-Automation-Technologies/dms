using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.ComponentModel;

using MSOpenKMCore.config;

namespace OutlookOpenKMAddIn.form
{
    public partial class ConfigurationForm : Form
    {
        private Button buttonCancel;
        private Label labelHost;
        private Label labelPassword;
        private Label labelUserName;
        private TextBox textBoxUserName;
        private TextBox textBoxPassword;
        private TextBox textBoxHost;
        private Button buttonAccept;
        private ComponentResourceManager resources = new ComponentResourceManager(typeof(ConfigurationForm));

        public ConfigurationForm()
        {
            InitializeComponent();
            this.Text = resources.GetString("configuration");
            this.CenterToParent();
            ConfigXML configXML = new ConfigXML();
            textBoxHost.Text = configXML.getHost();
            textBoxUserName.Text = configXML.getUser();
            textBoxPassword.Text = configXML.getPassword();
        }

        private void InitializeComponent()
        {
            this.buttonAccept = new System.Windows.Forms.Button();
            this.buttonCancel = new System.Windows.Forms.Button();
            this.labelHost = new System.Windows.Forms.Label();
            this.labelPassword = new System.Windows.Forms.Label();
            this.labelUserName = new System.Windows.Forms.Label();
            this.textBoxUserName = new System.Windows.Forms.TextBox();
            this.textBoxPassword = new System.Windows.Forms.TextBox();
            this.textBoxHost = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // buttonAccept
            // 
            this.buttonAccept.Location = new System.Drawing.Point(82, 125);
            this.buttonAccept.Name = "buttonAccept";
            this.buttonAccept.Size = new System.Drawing.Size(75, 23);
            this.buttonAccept.TabIndex = 0;
            this.buttonAccept.Text = resources.GetString("accept");
            this.buttonAccept.UseVisualStyleBackColor = true;
            this.buttonAccept.Click += new System.EventHandler(this.buttonAccept_Click);
            // 
            // buttonCancel
            // 
            this.buttonCancel.Location = new System.Drawing.Point(244, 125);
            this.buttonCancel.Name = "buttonCancel";
            this.buttonCancel.Size = new System.Drawing.Size(75, 23);
            this.buttonCancel.TabIndex = 1;
            this.buttonCancel.Text = resources.GetString("cancel");
            this.buttonCancel.UseVisualStyleBackColor = true;
            this.buttonCancel.Click += new System.EventHandler(this.buttonCancel_Click);
            // 
            // labelHost
            // 
            this.labelHost.AutoSize = true;
            this.labelHost.Location = new System.Drawing.Point(12, 86);
            this.labelHost.Name = "labelHost";
            this.labelHost.Size = new System.Drawing.Size(29, 13);
            this.labelHost.TabIndex = 2;
            this.labelHost.Text = resources.GetString("host");
            // 
            // labelPassword
            // 
            this.labelPassword.AutoSize = true;
            this.labelPassword.Location = new System.Drawing.Point(12, 54);
            this.labelPassword.Name = "labelPassword";
            this.labelPassword.Size = new System.Drawing.Size(53, 13);
            this.labelPassword.TabIndex = 3;
            this.labelPassword.Text = resources.GetString("password");
            // 
            // labelUserName
            // 
            this.labelUserName.AutoSize = true;
            this.labelUserName.Location = new System.Drawing.Point(12, 22);
            this.labelUserName.Name = "labelUserName";
            this.labelUserName.Size = new System.Drawing.Size(58, 13);
            this.labelUserName.TabIndex = 4;
            this.labelUserName.Text = resources.GetString("username");
            // 
            // textBoxUserName
            // 
            this.textBoxUserName.Location = new System.Drawing.Point(116, 19);
            this.textBoxUserName.Name = "textBoxUserName";
            this.textBoxUserName.Size = new System.Drawing.Size(165, 20);
            this.textBoxUserName.TabIndex = 5;
            // 
            // textBoxPassword
            // 
            this.textBoxPassword.Location = new System.Drawing.Point(116, 51);
            this.textBoxPassword.Name = "textBoxPassword";
            this.textBoxPassword.Size = new System.Drawing.Size(165, 20);
            this.textBoxPassword.TabIndex = 6;
            // 
            // textBoxHost
            // 
            this.textBoxHost.Location = new System.Drawing.Point(116, 83);
            this.textBoxHost.Name = "textBoxHost";
            this.textBoxHost.Size = new System.Drawing.Size(273, 20);
            this.textBoxHost.TabIndex = 7;
            // 
            // ConfigurationForm
            // 
            this.ClientSize = new System.Drawing.Size(409, 163);
            this.Controls.Add(this.textBoxHost);
            this.Controls.Add(this.textBoxPassword);
            this.Controls.Add(this.textBoxUserName);
            this.Controls.Add(this.labelUserName);
            this.Controls.Add(this.labelPassword);
            this.Controls.Add(this.labelHost);
            this.Controls.Add(this.buttonCancel);
            this.Controls.Add(this.buttonAccept);
            this.Name = "ConfigurationForm";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.Hide();
        }

        private void buttonAccept_Click(object sender, EventArgs e)
        {
            bool error = false;
            String errorTXT = "";

            if (textBoxUserName.Text.Equals(""))
            {
                error = true;
                errorTXT += String.Format(resources.GetString("error"), resources.GetString("username")) + "\n";
            }

            if (textBoxPassword.Text.Equals(""))
            {
                error = true;
                errorTXT += String.Format(resources.GetString("error"), resources.GetString("password")) + "\n";
            }

            if (textBoxHost.Text.Equals(""))
            {
                error = true;
                errorTXT += String.Format(resources.GetString("error"), resources.GetString("host")) + "\n";
            }

            if (error)
            {
                MessageBox.Show(errorTXT, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
            else
            {
                ConfigXML configXML = new ConfigXML();
                configXML.setHost(textBoxHost.Text);
                configXML.setUser(textBoxUserName.Text);
                configXML.setPassword(textBoxPassword.Text);
                configXML.CreateConfigurationFile();
                this.Hide();
            }
        }
    }
}
