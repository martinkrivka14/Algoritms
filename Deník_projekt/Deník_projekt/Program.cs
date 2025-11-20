using System.ComponentModel.DataAnnotations;
using System.Globalization;
using System.Text;

namespace Deník_projekt
{
    internal class Program
    {
        static void Main(string[] args)
        {
            LinkedList<string> liff = new LinkedList< string>();
            Seznam linkedList = new Seznam();

            DateTime date = new DateTime();

            linkedList.AddLast(date, "První prvek");

            Zaznam aktualniPrvek = linkedList.Posledni;

            bool end = false;

            while(end == false )
            {

                Console.WriteLine("------------------------------------------------------------");
                Console.WriteLine("Deník se ovládá následujícími příkazy:");
                Console.WriteLine("- predchozi: Přesunutí na předchozí záznam");
                Console.WriteLine("- dalsi: Přesunutí na další záznam");
                Console.WriteLine("- novy: Vytvoření nového záznamu");
                Console.WriteLine("- smaz: Odstranění záznamu");
                Console.WriteLine("- zavri: Zavření deníku");
                Console.WriteLine("------------------------------------------------------------");
                Console.WriteLine();
                Console.WriteLine("Počet záznamů: "+ linkedList.pocet +  "");
                Console.Write("Zadej příkaz: ");

                
                string volba = Console.ReadLine();

                switch(volba){
                    case "p":

                        if(aktualniPrvek.Predchozi == null)
                        {
                            throw new NullReferenceException("Záznam je v seznamu první");
                        }
                        
                        Console.WriteLine("Tvůj aktuální záznam byl");
                        Console.WriteLine(aktualniPrvek.ToString());

                        aktualniPrvek = aktualniPrvek.Predchozi;

                        Console.WriteLine("Momentálně jím je");
                        Console.WriteLine(aktualniPrvek.ToString());
                        
                        break;
                    case "n":

                        bool stop = false;
                        DateTime datumParse = new DateTime();

                        while (stop == false)
                        {
                            Console.WriteLine("Napiš datum přidání ve formátu dd.MM.yyyy nebo d.M.yyyy");
                            string datum = Console.ReadLine();

                            string[] formats = { "dd.MM.yyyy", "d.M.yyyy" };


                            if (DateTime.TryParseExact(datum, formats,
                                new CultureInfo("cs-CZ"), DateTimeStyles.None, out datumParse))
                            {
                                Console.WriteLine("OK");
                                stop = true;
                            }
                            else
                            {
                                Console.WriteLine("Špatný formát");
                            }
                        }


                       

                        StringBuilder textBuilder = new StringBuilder();
                        string radek;
                        string ukoncovaciZnak = "uloz";

                        Console.WriteLine("Text:");
                        while (true)
                        {
                            radek = Console.ReadLine();

                            if (radek.Equals(ukoncovaciZnak, StringComparison.OrdinalIgnoreCase))
                            {
                                break;
                            }

                            textBuilder.AppendLine(radek);
                        }

                        string vyslednyText = textBuilder.ToString();


                        Console.WriteLine("--- Uložený text ---");
                        linkedList.AddAfter(aktualniPrvek,datumParse, vyslednyText);
                        Console.WriteLine(vyslednyText);


                        aktualniPrvek = aktualniPrvek.Dalsi;




                        break;
                    case "d":

                        if (aktualniPrvek.Dalsi == null)
                        {
                            throw new NullReferenceException("Záznam je v seznamu poslední");
                        }

                        Console.WriteLine("Tvůj aktuální záznam byl");
                        Console.WriteLine(aktualniPrvek.ToString());

                        aktualniPrvek = aktualniPrvek.Dalsi;

                        Console.WriteLine("Momentálně jím je");
                        Console.WriteLine(aktualniPrvek.ToString());

                        break;
                    case "s":

                        if (aktualniPrvek == null)
                        {
                            throw new NullReferenceException("Seznam je prázdný");
                           
                        }

                        Console.WriteLine("Chces smazat zaznam a/n:");
                        Console.WriteLine(aktualniPrvek.ToString());
                        string smazat = Console.ReadLine();

                        

                        if (smazat == "a")
                        {
                            
                            Console.WriteLine("Smazal jsi prvek");
                            Console.WriteLine(aktualniPrvek.ToString());

                            

                      
                            Zaznam tempPrvek = aktualniPrvek.Predchozi;
                            
                            if(aktualniPrvek.Predchozi == null)
                            {
                                tempPrvek = aktualniPrvek.Dalsi;
                            }

                            linkedList.RemoveThis(aktualniPrvek);
                            aktualniPrvek = tempPrvek;
                            




                            if (linkedList.pocet > 0)
                            {
                                Console.WriteLine("Zde je první prvek listu");
                                Console.WriteLine(linkedList.Prvni.ToString());
                            }
                            else
                            {
                                break;
                            }
                            
                        }
                        else
                        {
                            Console.WriteLine("Zvolil jsi možnost nesmazaní zaznámu");
                            break;
                        }
                            break;
                    case "z": end = true; Console.WriteLine("Děkujeme za využití našeho programu");  break;
                }
            }
        }
    }
}
