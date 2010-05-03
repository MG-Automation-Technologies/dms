using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WordOpenKMAddIn.logic
{
    [Serializable()]
    public class OKMException : System.Exception
    {
        public OKMException() : base() { }
        public OKMException(string message) : base(message) { }
        public OKMException(string message, System.Exception inner) : base(message, inner) { }

        // Constructor needed for serialization 
        // when exception propagates from a remoting server to the client. 
        protected OKMException(System.Runtime.Serialization.SerializationInfo info,
            System.Runtime.Serialization.StreamingContext context) { }
    }

}
