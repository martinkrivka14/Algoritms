using System;
using System.Collections.Generic;
using System.ComponentModel.Design;
using System.Diagnostics;
using System.Reflection;

namespace Sorting_Algorithms
{
    internal class Program
    {
        static void Main(string[] args)
        {
            
            TimeSpan interval = TimeSpan.FromSeconds(3600);

            string[] docNumbers = File.ReadAllLines("random_integers_10M.txt");
            string[] docWords = File.ReadAllLines("random_words_10M.txt");

            Console.WriteLine("Files read");



            int[] numbers = new int[docNumbers.Length];

            for (int i = 0; i < docNumbers.Length; i++)
                numbers[i] = int.Parse(docNumbers[i]);

            Console.WriteLine("Integers added to array");

            List<int> listNumbers = new List<int>(numbers);


            string[] words = new string[docWords.Length];
            for (int i = 0; i < docWords.Length; i++)
                words[i] = docWords[i];

            Console.WriteLine("Strings added to array");

            List<string> listWords = new List<string>(words);

            Console.WriteLine("Sorting...");





            //done

            //selectionSortInt(numbers, interval);

            //selectionSortString(words,interval);

            //bubbleSortInt(numbers, interval);
            //bubbleSortString(words, interval);

            //insertionSortInt(numbers, interval);
            //insertionSortString(words, interval);


            Stopwatch sw = Stopwatch.StartNew();



            //heapSortInt(listNumbers);
            //heapSortString(listWords);


            //mergeSortInt(numbers);
            //mergeSortString(words);

            //quickSortInt(numbers, 0, numbers.Length - 1);





            //quickSortString(words);






            //radixSortString(words);

            

           radixSort(numbers, numbers.Length);

            sw.Stop();
            infoDone(sw.ToString());



            Console.WriteLine("Sorting done");
        }

        //count
        static void countSelInt(int[] notSorted)
        {


            int end = 0;

            for (int i = 0; i < notSorted.Length - 1; i++)
            {
                if (notSorted[i] <= notSorted[i + 1])
                {
                    end = i;
                }
                else
                {
                    break;
                }
            }

            double percentage = (double)end / (notSorted.Length - 1) * 100;

            Console.WriteLine("Array was not sorted - sorting numbers");
            Console.WriteLine("Percentage was " + percentage + " %");
        }




        static void countSelString(string[] notSorted)
        {

            int end = 0;

            
            for (int i = 0; i < notSorted.Length - 1; i++)
            {
                
                if (string.Compare(notSorted[i], notSorted[i + 1]) <= 0)
                {
                    end++;
                }
                else
                {
                    break;
                }
            }

            
            double percentage = (double)end / (notSorted.Length - 1) * 100;

            Console.WriteLine("Array was not sorted - sorting strings");
            Console.WriteLine("Percentage was " + percentage+ " %");

        }


        //info

        static void infoDone(string time)
        {
            Console.WriteLine("Array was 100% sorted");
            Console.WriteLine("Time to sort was " + time);
        }

        //SORTS


        //selection

        static void selectionSortInt(int[] array, TimeSpan limit)
        {
            Stopwatch sw = Stopwatch.StartNew();
            int temp, min;
            for (int i = 0; i < (array.Length - 1); i++)
            {
                min = array.Length - 1;
                for (int j = i; j < (array.Length - 1); j++)
                    if (array[min] > array[j])
                        min = j;
                temp = array[min];
                array[min] = array[i];
                array[i] = temp;
                if(sw.Elapsed > limit)
                {
                    Console.WriteLine("Time Elapsed");
                    countSelInt(array);
                    return;
                }
            }
            infoDone(sw.ToString());
            return;
        }

        static void selectionSortString(string[] array,TimeSpan limit)
        {

            Stopwatch sw = Stopwatch.StartNew();
            int min;
            string temp;


            for (int i = 0; i < array.Length - 1; i++)
            {
                min = i;
                for (int j = i + 1; j < array.Length; j++)
                {
                    if (string.Compare(array[j], array[min]) < 0)
                    {
                        min = j;
                    }
                }
                temp = array[min];
                array[min] = array[i];
                array[i] = temp;

                if (sw.Elapsed > limit)
                {
                    Console.WriteLine("Time Elapsed");
                    countSelString(array);
                    return;
                }
            }
            infoDone(sw.ToString());
            return;
        }


        //bubble

        static void bubbleSortInt(int[] array, TimeSpan limit)
        {
            Stopwatch sw = Stopwatch.StartNew();
            int j = array.Length - 2, temp;
            bool swapped = true;
            while (swapped)
            {
                swapped = false;
                for (int i = 0; i <= j; i++)
                {
                    if (array[i] > array[i + 1])
                    {
                        temp = array[i];
                        array[i] = array[i + 1];
                        array[i + 1] = temp;
                        swapped = true;
                    }
                }
                j--;
                if(sw.Elapsed > limit)
                {
                    Console.WriteLine("Time Elapsed");
                    
                    double done = array.Length - 2 - j;
                    double percentage = (done / (array.Length - 1)) * 100;

                    
                    if (percentage < 0) percentage = 0;

                    Console.WriteLine("Array was not sorted - sorting strings");
                    Console.WriteLine("Percentage was " + percentage + " %");
                    return;
                }
            }
            infoDone(sw.ToString());
            return;
        }

        static void bubbleSortString(string[] array, TimeSpan limit)
        {
            Stopwatch sw = Stopwatch.StartNew();
            int j = array.Length - 2;
            string temp;
            bool swapped = true;
            while (swapped)
            {
                swapped = false;
                for (int i = 0; i <= j; i++)
                {
                    if (string.Compare(array[i], array[i + 1], true) > 0)
                    {
                        temp = array[i];
                        array[i] = array[i + 1];
                        array[i + 1] = temp;
                        swapped = true;
                    }
                }
                j--;
                if(sw.Elapsed > limit)
                {
                    Console.WriteLine("Time Elapsed");

                    double done = array.Length - 2 - j;
                    double percentage = (done / (array.Length - 1)) * 100;


                    if (percentage < 0) percentage = 0;

                    Console.WriteLine("Array was not sorted - sorting strings");
                    Console.WriteLine("Percentage was " + percentage + " %");
                    return;
                }
            }
            infoDone(sw.ToString());
            return;
        }


        //insertion

        static void insertionSortInt(int[] array,TimeSpan limit)
        {
            Stopwatch sw = Stopwatch.StartNew();
            int item, j;
            for (int i = 1; i <= (array.Length - 1); i++)
            {
                item = array[i];
                j = i - 1;
                while ((j >= 0) && (array[j] > item))
                {
                    array[j + 1] = array[j];
                    j--;
                }
                array[j + 1] = item;
                if(sw.Elapsed > limit)
                {
                    Console.WriteLine("Time Elapsed");
                    countSelInt(array);
                    return;
                }
            }
            infoDone(sw.ToString());
            return;
        }

        static void insertionSortString(string[] array, TimeSpan limit)
        {
            Stopwatch sw = Stopwatch.StartNew();
            string item;
            int j;
            for (int i = 1; i <= (array.Length - 1); i++)
            {
                item = array[i];
                j = i - 1;
                while ((j >= 0) && (string.Compare( array[j], item) > 0))
                {
                    array[j + 1] = array[j];
                    j--;
                }
                array[j + 1] = item;
                if (sw.Elapsed > limit)
                {
                    Console.WriteLine("Time Elapsed");
                    countSelString(array);
                    return;
                }
            }
            infoDone(sw.ToString());
            return;
        }

        //heap



        static void heapSortInt(List<int> arr)
        {

            Stopwatch sw = Stopwatch.StartNew();
            int n = arr.Count;

            for (int i = n / 2 - 1; i >= 0; i--)
                heapifyInt(arr, n, i);

            for (int i = n - 1; i > 0; i--)
            {
                int temp = arr[0];
                arr[0] = arr[i];
                arr[i] = temp;

                heapifyInt(arr, i, 0);
            }
            infoDone(sw.ToString());
            return;
        }

        static void heapifyInt(List<int> arr, int n, int i)
        {
            int largest = i;

            int l = 2 * i + 1;

            int r = 2 * i + 2;

            if (l < n && arr[l] > arr[largest])
                largest = l;

            if (r < n && arr[r] > arr[largest])
                largest = r;

            if (largest != i)
            {
                int temp = arr[i];
                arr[i] = arr[largest];
                arr[largest] = temp;

                heapifyInt(arr, n, largest);
            }
        }





        static void heapSortString(List<string> array)
        {
            Stopwatch sw = Stopwatch.StartNew();
            int n = array.Count;


            for (int i = n / 2 - 1; i >= 0; i--)
                heapifyString(array, n, i);

            for (int i = n - 1; i > 0; i--)
            {
                string temp = array[0];
                array[0] = array[i];
                array[i] = temp;

                heapifyString(array, i, 0);
            }
            sw.Stop();
            infoDone(sw.ToString());
            return;
        }

        static void heapifyString(List<string> arr, int n, int i)
        {
            int largest = i;

            int l = 2 * i + 1;

            int r = 2 * i + 2;

            if (l < n && string.Compare(arr[l], arr[largest]) > 0)
                largest = l;

            if (r < n && string.Compare(arr[r],arr[largest]) > 0)
                largest = r;

            if (largest != i)
            {
                string temp = arr[i];
                arr[i] = arr[largest];
                arr[largest] = temp;

                heapifyString(arr, n, largest);
            }

            
        }

        //merge


        public static void mergeInt(int[] list, int[] left, int[] right)
        {
            int i = 0;
            int j = 0;
            while ((i < left.Length) && (j < right.Length))
            {
                if (left[i] < right[j])
                {
                    list[i + j] = left[i];
                    i++;
                }
                else
                {
                    list[i + j] = right[j];
                    j++;
                }
            }
            if (i < left.Length)
            {
                while (i < left.Length)
                {
                    list[i + j] = left[i];
                    i++;
                }
            }
            else
            {
                while (j < right.Length)
                {
                    list[i + j] = right[j];
                    j++;
                }
            }
        }

        
        public static void mergeSortInt(int[] list)
        {
            Stopwatch sw = Stopwatch.StartNew();
            if (list.Length <= 1) 
                return;

            int center = list.Length / 2;

            int[] left = new int[center];

            for (int i = 0; i < center; i++)
            {
                left[i] = list[i];
            }
                

            int[] right = new int[list.Length - center];

            for (int i = center; i < list.Length; i++) 
            {
                right[i - center] = list[i];
            }
                
                

            mergeSortInt(left);
            mergeSortInt(right);
            mergeInt(list, left, right);
        }



        public static void mergeString(string[] list, string[] left, string[] right)
        {
            int i = 0;
            int j = 0;
            
            while ((i < left.Length) && (j < right.Length))
            {
                
                if (string.Compare(left[i], right[j]) < 0) 
                {
                    list[i + j] = left[i];
                    i++;
                }
                else
                {
                    list[i + j] = right[j];
                    j++;
                }
            }
            
            if (i < left.Length)
            {
                while (i < left.Length)
                {
                    list[i + j] = left[i];
                    i++;
                }
            }
            else
            {
                while (j < right.Length)
                {
                    list[i + j] = right[j];
                    j++;
                }
            }
        }

        
        public static void mergeSortString(string[] list)
        {
            if (list.Length <= 1)
                return;

            int center = list.Length / 2; 

            string[] left = new string[center]; 

            for (int i = 0; i < center; i++)
                left[i] = list[i];

            string[] right = new string[list.Length - center]; 

            for (int i = center; i < list.Length; i++)
                right[i - center] = list[i];

            mergeSortString(left);
            mergeSortString(right);
            mergeString(list, left, right); 

        }





        //quick

        static int partitionInt(int[] arr, int low, int high)
        {

            int pivot = arr[high];

 
            int i = low - 1;

            for (int j = low; j <= high - 1; j++)
            {
                if (arr[j] < pivot)
                {
                    i++;
                    swapInt(arr, i, j);
                }
            }
            swapInt(arr, i + 1, high);
            return i + 1;
        }


        static void swapInt(int[] arr, int i, int j)
        {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

       
        static void quickSortInt(int[] arr, int low, int high)
        {
            if (low < high)
            {
                int pi = partitionInt(arr, low, high);

                quickSortInt(arr, low, pi - 1);
                quickSortInt(arr, pi + 1, high);
            }
        }





        public static void quickSortString(string[] arr)
        {
            var stack = new Stack<(int, int)>();
            stack.Push((0, arr.Length - 1));

            while (stack.Count > 0)
            {
                var (low, high) = stack.Pop();
                if (low >= high) continue;

                int p = Partition(arr, low, high);

                
                if (p - low < high - p)
                {
                    stack.Push((p + 1, high));
                    stack.Push((low, p));
                }
                else
                {
                    stack.Push((low, p));
                    stack.Push((p + 1, high));
                }
            }
        }

        static int Partition(string[] arr, int low, int high)
        {
            string pivot = arr[Random.Shared.Next(low, high + 1)];

            int i = low - 1;
            int j = high + 1;

            while (true)
            {
                do { i++; } while (string.CompareOrdinal(arr[i], pivot) < 0);
                do { j--; } while (string.CompareOrdinal(arr[j], pivot) > 0);

                if (i >= j) return j;

                (arr[i], arr[j]) = (arr[j], arr[i]);
            }
        }



        //radix



        public static int getMax(int[] arr, int n)
        {
            int mx = arr[0];
            for (int i = 1; i < n; i++)
                if (arr[i] > mx)
                    mx = arr[i];
            return mx;
        }

        public static void countSort(int[] arr, int n, int exp)
        {
            int[] output = new int[n];
            int[] count = new int[10];

            
            for (int i = 0; i < n; i++)
                count[(arr[i] / exp) % 10]++;

            
            for (int i = 1; i < 10; i++)
                count[i] += count[i - 1];

            
            for (int i = n - 1; i >= 0; i--)
            {
                output[count[(arr[i] / exp) % 10] - 1] = arr[i];
                count[(arr[i] / exp) % 10]--;
            }

           
            for (int i = 0; i < n; i++)
                arr[i] = output[i];
        }

        public static void radixSort(int[] arr, int n)
        {
            if (n <= 1) return ;

            
            int negCount = 0;
            for (int i = 0; i < n; i++)
                if (arr[i] < 0) negCount++;

            int[] negatives = new int[negCount];
            int[] positives = new int[n - negCount];

            int negIdx = 0, posIdx = 0;
            for (int i = 0; i < n; i++)
            {
                if (arr[i] < 0)
                    negatives[negIdx++] = -arr[i];
                else
                    positives[posIdx++] = arr[i];
            }


            if (negCount > 0) radixSortInternal(negatives, negatives.Length);
            if (positives.Length > 0) radixSortInternal(positives, positives.Length);


            int index = 0;

            for (int i = negCount - 1; i >= 0; i--)
                arr[index++] = -negatives[i];

            for (int i = 0; i < positives.Length; i++)
                arr[index++] = positives[i];
        }

        private static void radixSortInternal(int[] arr, int n)
        {
            int m = getMax(arr, n);
            for (int exp = 1; m / exp > 0; exp *= 10)
                countSort(arr, n, exp);
        }







        static void radixSortString(string[] arr)
        {
            int maxLen = arr.Max(s => s.Length);

            for (int pos = maxLen - 1; pos >= 0; pos--)
                CountingSortString(arr, pos);
        }

        static void CountingSortString(string[] arr, int pos)
        {
            int n = arr.Length;
            string[] output = new string[n];
            int[] count = new int[27];

            foreach (string s in arr)
            {
                int index = pos < s.Length ? char.ToLower(s[pos]) - 'a' + 1 : 0;
                count[index]++;
            }

            for (int i = 1; i < 27; i++)
                count[i] += count[i - 1];

            for (int i = n - 1; i >= 0; i--)
            {
                int index = pos < arr[i].Length ? char.ToLower(arr[i][pos]) - 'a' + 1 : 0;
                output[--count[index]] = arr[i];
            }

            for (int i = 0; i < n; i++)
                arr[i] = output[i];
        }
    }
}
