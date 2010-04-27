using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WordOpenKMAddIn.bean
{
    class CommandBarOption
    {
        private bool edit = false;
        private bool checkin = false;
        private bool cancelCheckin = false;
        private bool add = false;

        public CommandBarOption()
        {
        }

        public void setEdit(bool edit) {
            this.edit = edit;
        }

        public void setCheckin(bool checkin)
        {
            this.checkin = checkin;
        }

        public void setCancelCheckin(bool cancelCheckin)
        {
            this.cancelCheckin = cancelCheckin;
        }

        public void setAdd(bool add)
        {
            this.add = add;
        }

        public bool isEdit()
        {
            return edit;
        }

        public bool isCheckin()
        {
            return checkin;
        }

        public bool isCancelCheckin()
        {
            return cancelCheckin;
        }

        public bool isAdd()
        {
            return add;
        }
    }
}
