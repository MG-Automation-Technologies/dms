using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MSOpenKMCore.bean
{
    [Serializable]
    public class OKMDocument
    {
        private String name = "";
        private String UUID = "";
        private String localFilename = "";
        private String path = "";
        private String type = "";

        public OKMDocument()
        {
        }

        public void setLocalFilename(String localFilename) {
            this.localFilename = localFilename;
        }

        public void setPath(String path)
        {
            this.path = path;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public void setUUID(String UUID)
        {
            this.UUID = UUID;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLocalFilename()
        {
            return localFilename;
        }

        public String getPath() {
            return path;
        }

        public String getName()
        {
            return name;
        }

        public String getUUID()
        {
            return UUID;
        }

        public String getType(){
            return type;
        }
    }
}
