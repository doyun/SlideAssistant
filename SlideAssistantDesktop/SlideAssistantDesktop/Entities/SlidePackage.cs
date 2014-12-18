using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SlideAssistantDesktop.Entities
{
    class SlidePackage
    {
        public string id { get; set; }
        public string type { get; set; }
        public string name { get; set; }
        public string slideNumber { get; set; }
        public string connectionsNumber { get; set; }
        public string isClear { get; set; }
        public string clear { get; set; }
        public string question { get; set; }
        public string wasSetClear {get; set;}

    }
}
