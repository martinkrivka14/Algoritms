using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Deník_projekt
{
    class Seznam
    {

        public Zaznam Prvni { get; private set; }
        public Zaznam Posledni { get; private set; }

        public int pocet;



        public void AddFirst(DateTime datum, string text)
        {
            Zaznam zaznam = new Zaznam(datum, text);

            if(Prvni == null)
            {
                Prvni = zaznam;
                Posledni = zaznam;
            }
            else
            {
                zaznam.Dalsi = Prvni;
                Prvni.Predchozi = zaznam;
                Prvni = zaznam;
            }
            pocet++;
        }


        public void AddLast(DateTime datum, string text)
        {
            Zaznam zaznam = new Zaznam(datum, text);

            if(Prvni == null)
            {
                Prvni = zaznam;
                Posledni = zaznam;
            }
            else
            {
                Posledni.Dalsi = zaznam;
                zaznam.Predchozi = Posledni;
               
                Posledni = zaznam;
            }
            pocet++;
        }

        public void AddAfter(Zaznam zaznam, DateTime datum, string text)
        {
            if(zaznam == null)
            {
                return;
            }

            if(zaznam == Posledni)
            {
                AddLast(datum, text);
                return;
            }

            Zaznam novy = new Zaznam(datum, text);
            Zaznam nasledujici = zaznam.Dalsi;


            zaznam.Dalsi = novy;
            novy.Predchozi = zaznam;

            novy.Dalsi = nasledujici;

            if(nasledujici != null)
            {
                nasledujici.Predchozi = novy;
            }
            pocet++;
        }

        public void AddBefore(Zaznam zaznam, DateTime datum, string text)
        {
            if(zaznam == null)
            {
                return;
            }


            if(zaznam == Prvni)
            {
                AddFirst(datum, text);
                return;
            }


            Zaznam novy = new Zaznam(datum, text);
            Zaznam predchozi = zaznam.Predchozi;

            novy.Dalsi = zaznam;
            zaznam.Predchozi = novy;


            novy.Predchozi = predchozi;
            predchozi.Dalsi = novy;

            pocet++;
        }

        public void RemoveFirst()
        {
            if (Prvni == null)
            {
                return;
            }

            if(Prvni == Posledni)
            {
                Prvni = null;
                Posledni = null;
                pocet = 0;
                return;
                
            }
            else
            {

                Prvni = Prvni.Dalsi;
                Prvni.Predchozi = null;
                pocet--;
            }
        }

        public void RemoveLast()
        {
            if(Prvni == null)
            {
                return;
            }
            if(Prvni == Posledni)
            {
                Prvni = null;
                Posledni = null;
                pocet = 0;
                return;
            }
            else
            {
                Posledni = Posledni.Predchozi;
                Posledni.Dalsi = null;
                pocet--;
            }
        }


        public void RemoveThis(Zaznam zaznam)
        {
            if(Prvni == zaznam)
            {
                RemoveFirst();
            }
            else if(Posledni == zaznam)
            {
                RemoveLast();
            }
            else
            {
                Zaznam predchozi = zaznam.Predchozi;
                Zaznam dalsi = zaznam.Dalsi;

                predchozi.Dalsi = dalsi;
                dalsi.Predchozi = predchozi;

                pocet--;
            }
        }

    }
}
