using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Deník_projekt
{
    class Zaznam
    {
        public DateTime Datum { get; set; }
        public string Text { get; set; }


        public Zaznam Dalsi { get; set; }
        public Zaznam Predchozi { get; set; }


        public Zaznam(DateTime datum, string text)
        {
            Datum = datum;
            Text = text;
            Dalsi = null;
            Predchozi = null; 
        }


        public override string ToString()
        {
            return $"Úkol na {Datum} s textem {Text}";
        }

    }
}
