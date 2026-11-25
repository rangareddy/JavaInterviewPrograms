# Java Interview Programs 🎯

!["Java Interview Programs"](./images/Java_Interview_Programs.png)

![Java](https://img.shields.io/badge/Java-8%2B-E34F26?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)
![Programs](https://img.shields.io/badge/Programs-473-0078D4)
![Topics](https://img.shields.io/badge/Topics-18-2E8B57)
![Stars](https://img.shields.io/github/stars/rangareddy/JavaInterviewPrograms?style=social)

473 interview problems solved in plain Java, each one a standalone class you can run, with a write-up that walks from the brute-force idea to the optimized solution.

The set covers the Blind 75, NeetCode 150, the Kohei Arai "60 LeetCode questions" list, the LeetCode problems that ten or more companies ask about, and a block of classic Java core and concurrency questions. Nothing is listed twice.

If it helps your preparation, a star ⭐ on the repository is appreciated.

## Table of Contents 📖

- **[At a Glance 📊](#at-a-glance-)**
- **[How Each Problem Is Written ✍️](#how-each-problem-is-written-%EF%B8%8F)**
- **[Coding Interview Questions by Topic 🗂️](#coding-interview-questions-by-topic)**
- **[Pattern Cheat Sheet 💡](#pattern-cheat-sheet-)**
- **[Suggested Study Plan 🗓️](#suggested-study-plan-%EF%B8%8F)**
- **[Repository Structure 🧱](#repository-structure-)**
- **[How to Run 🚀](#how-to-run-)**
- **[Contributing 🤝](#contributing-)**

## At a Glance 📊

| Difficulty | Programs |
|------------|----------|
| 🟢 Easy | 113 |
| 🟡 Medium | 280 |
| 🔴 Hard | 80 |
| **Total** | **473** |

| Topic | Name | Programs |
|-------|------|----------|
| `01` | [Arrays & Strings](#topic-01) | 115 |
| `02` | [Hashing](#topic-02) | 24 |
| `03` | [Linked Lists](#topic-03) | 24 |
| `04` | [Stacks & Queues](#topic-04) | 28 |
| `05` | [Binary Search](#topic-05) | 19 |
| `06` | [Sorting & Intervals](#topic-06) | 20 |
| `07` | [Trees & BST](#topic-07) | 38 |
| `08` | [Heaps](#topic-08) | 17 |
| `09` | [Graphs](#topic-09) | 37 |
| `10` | [Dynamic Programming](#topic-10) | 28 |
| `11` | [Backtracking](#topic-11) | 17 |
| `12` | [Greedy Algorithms](#topic-12) | 16 |
| `13` | [Tries](#topic-13) | 5 |
| `14` | [Bit Manipulation](#topic-14) | 9 |
| `15` | [Advanced Graphs](#topic-15) | 12 |
| `16` | [Advanced DP](#topic-16) | 15 |
| `17` | [Design Problems](#topic-17) | 26 |
| `18` | [Java Core & Concurrency](#topic-18) | 23 |

## How Each Problem Is Written ✍️

Every program follows the same shape, so you always know where to look.

| Part | What you get |
|------|--------------|
| Java class | A standalone class under `src/main/java/com/ranga/<topic>/` with a Javadoc: problem statement, strategy, a worked example and the complexity. |
| `main` method | Sample data printed as aligned `Input  :` and `Output :` lines, plus an edge case such as `null` or empty input. |
| Markdown page | Strategy, then an **Approaches** section from brute force to optimized, each with code and time and space complexity, a comparison table, the full source, and a traced **Solution** walkthrough. |
| README row | The problem, a difficulty signal (🟢 🟡 🔴) and the core idea, linked to the page. |

The code in the Approaches section is compiled and compared with the repository class on many inputs before it is committed. Where a page describes an optimized variant that differs from the class in `src`, the page says so.

## Coding Interview Questions by Topic

Interview problems grouped by topic, in a recommended study order. Each page explains the strategy, shows several approaches from brute force to optimized, states the time and space complexity, and includes the Java source. Every program appears exactly once, under the topic where it is best learned.

### 1. Complete topic coverage

18 topics, 473 programs. Pick a card to jump to its problems.

<table>
<tr><td width="33%" align="center" valign="top"><a href="#topic-01"><img src="./images/topics/01-arrays-strings.svg" width="56" height="56" alt="Arrays &amp; Strings"></a><br><code>01</code> <b><a href="#topic-01">Arrays &amp; Strings</a></b><br><sub>Two pointers, prefix sums, sliding window</sub><br><sub><b>115</b> problems</sub></td><td width="33%" align="center" valign="top"><a href="#topic-02"><img src="./images/topics/02-hashing.svg" width="56" height="56" alt="Hashing"></a><br><code>02</code> <b><a href="#topic-02">Hashing</a></b><br><sub>HashMap, HashSet, frequency maps</sub><br><sub><b>24</b> problems</sub></td><td width="33%" align="center" valign="top"><a href="#topic-03"><img src="./images/topics/03-linked-lists.svg" width="56" height="56" alt="Linked Lists"></a><br><code>03</code> <b><a href="#topic-03">Linked Lists</a></b><br><sub>Reversal, cycles, merge, pointers</sub><br><sub><b>24</b> problems</sub></td></tr>
<tr><td width="33%" align="center" valign="top"><a href="#topic-04"><img src="./images/topics/04-stacks-queues.svg" width="56" height="56" alt="Stacks &amp; Queues"></a><br><code>04</code> <b><a href="#topic-04">Stacks &amp; Queues</a></b><br><sub>Monotonic stack, deque, expressions</sub><br><sub><b>28</b> problems</sub></td><td width="33%" align="center" valign="top"><a href="#topic-05"><img src="./images/topics/05-binary-search.svg" width="56" height="56" alt="Binary Search"></a><br><code>05</code> <b><a href="#topic-05">Binary Search</a></b><br><sub>Search spaces, rotated arrays</sub><br><sub><b>19</b> problems</sub></td><td width="33%" align="center" valign="top"><a href="#topic-06"><img src="./images/topics/06-sorting-intervals.svg" width="56" height="56" alt="Sorting &amp; Intervals"></a><br><code>06</code> <b><a href="#topic-06">Sorting &amp; Intervals</a></b><br><sub>Merge intervals, custom sorting</sub><br><sub><b>20</b> problems</sub></td></tr>
<tr><td width="33%" align="center" valign="top"><a href="#topic-07"><img src="./images/topics/07-trees-bst.svg" width="56" height="56" alt="Trees &amp; BST"></a><br><code>07</code> <b><a href="#topic-07">Trees &amp; BST</a></b><br><sub>DFS, BFS, traversal, construction</sub><br><sub><b>38</b> problems</sub></td><td width="33%" align="center" valign="top"><a href="#topic-08"><img src="./images/topics/08-heaps.svg" width="56" height="56" alt="Heaps"></a><br><code>08</code> <b><a href="#topic-08">Heaps</a></b><br><sub>PriorityQueue, Top K, K-way merge</sub><br><sub><b>17</b> problems</sub></td><td width="33%" align="center" valign="top"><a href="#topic-09"><img src="./images/topics/09-graphs.svg" width="56" height="56" alt="Graphs"></a><br><code>09</code> <b><a href="#topic-09">Graphs</a></b><br><sub>BFS, DFS, Dijkstra, Union-Find</sub><br><sub><b>37</b> problems</sub></td></tr>
<tr><td width="33%" align="center" valign="top"><a href="#topic-10"><img src="./images/topics/10-dynamic-programming.svg" width="56" height="56" alt="Dynamic Programming"></a><br><code>10</code> <b><a href="#topic-10">Dynamic Programming</a></b><br><sub>Memoization, tabulation, optimization</sub><br><sub><b>28</b> problems</sub></td><td width="33%" align="center" valign="top"><a href="#topic-11"><img src="./images/topics/11-backtracking.svg" width="56" height="56" alt="Backtracking"></a><br><code>11</code> <b><a href="#topic-11">Backtracking</a></b><br><sub>Permutations, combinations, subsets</sub><br><sub><b>17</b> problems</sub></td><td width="33%" align="center" valign="top"><a href="#topic-12"><img src="./images/topics/12-greedy.svg" width="56" height="56" alt="Greedy Algorithms"></a><br><code>12</code> <b><a href="#topic-12">Greedy Algorithms</a></b><br><sub>Scheduling, selection, optimization</sub><br><sub><b>16</b> problems</sub></td></tr>
<tr><td width="33%" align="center" valign="top"><a href="#topic-13"><img src="./images/topics/13-tries.svg" width="56" height="56" alt="Tries"></a><br><code>13</code> <b><a href="#topic-13">Tries</a></b><br><sub>Prefix search, autocomplete</sub><br><sub><b>5</b> problems</sub></td><td width="33%" align="center" valign="top"><a href="#topic-14"><img src="./images/topics/14-bit-manipulation.svg" width="56" height="56" alt="Bit Manipulation"></a><br><code>14</code> <b><a href="#topic-14">Bit Manipulation</a></b><br><sub>XOR, masks, swapping, bit operations</sub><br><sub><b>9</b> problems</sub></td><td width="33%" align="center" valign="top"><a href="#topic-15"><img src="./images/topics/15-advanced-graphs.svg" width="56" height="56" alt="Advanced Graphs"></a><br><code>15</code> <b><a href="#topic-15">Advanced Graphs</a></b><br><sub>Topological sort, MST, SCC</sub><br><sub><b>12</b> problems</sub></td></tr>
<tr><td width="33%" align="center" valign="top"><a href="#topic-16"><img src="./images/topics/16-advanced-dp.svg" width="56" height="56" alt="Advanced DP"></a><br><code>16</code> <b><a href="#topic-16">Advanced DP</a></b><br><sub>Knapsack, interval DP, tree DP</sub><br><sub><b>15</b> problems</sub></td><td width="33%" align="center" valign="top"><a href="#topic-17"><img src="./images/topics/17-design-problems.svg" width="56" height="56" alt="Design Problems"></a><br><code>17</code> <b><a href="#topic-17">Design Problems</a></b><br><sub>LRU, LFU, data structures</sub><br><sub><b>26</b> problems</sub></td><td width="33%" align="center" valign="top"><a href="#topic-18"><img src="./images/topics/18-java-core-concurrency.svg" width="56" height="56" alt="Java Core &amp; Concurrency"></a><br><code>18</code> <b><a href="#topic-18">Java Core &amp; Concurrency</a></b><br><sub>Core Java basics, threads, locks, streams</sub><br><sub><b>23</b> problems</sub></td></tr>
</table>

<a id="topic-01"></a>

### <img src="./images/topics/01-arrays-strings.svg" width="32" height="32" align="absmiddle"> 01. Arrays & Strings

*Two pointers, prefix sums, sliding window*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 1 | [Second Largest Element in an Array](./md_files/array/SecondLargestElement.md) | 🟢 Easy | Single pass |
| 2 | [Best Time to Buy and Sell Stock](./md_files/array/BestTimeToBuyAndSellStock.md) | 🟢 Easy | Greedy |
| 3 | [Maximum Subarray](./md_files/array/MaximumSubarray.md) | 🟡 Medium | Kadane's algorithm |
| 4 | [Product of Array Except Self](./md_files/array/ProductOfArrayExceptSelf.md) | 🟡 Medium | Prefix/suffix |
| 5 | [Maximum Product Subarray](./md_files/array/MaximumProductSubarray.md) | 🟡 Medium | Dynamic tracking |
| 6 | [Majority Element](./md_files/array/MajorityElement.md) | 🟢 Easy | Boyer-Moore |
| 7 | [Majority Element II](./md_files/array/MajorityElementII.md) | 🟡 Medium | Extended Boyer-Moore |
| 8 | [Missing Number](./md_files/array/MissingNumber.md) | 🟢 Easy | XOR / Math |
| 9 | [Find All Numbers Disappeared in an Array](./md_files/array/FindAllNumbersDisappeared.md) | 🟢 Easy | In-place marking |
| 10 | [First Missing Positive](./md_files/array/FirstMissingPositive.md) | 🔴 Hard | Index placement |
| 11 | [Merge Sorted Array](./md_files/array/MergeSortedArray.md) | 🟢 Easy | Two pointers |
| 12 | [Rotate an Array](./md_files/array/RotateAnArray.md) | 🟡 Medium | Reversal |
| 13 | [Next Permutation](./md_files/array/NextPermutation.md) | 🟡 Medium | Lexicographic order |
| 14 | [Sort Colors](./md_files/array/MoveZerosAndOnes.md) | 🟡 Medium | Dutch National Flag |
| 15 | [Remove Duplicates from Sorted Array](./md_files/array/RemoveDuplicates.md) | 🟢 Easy | In-place pointers |
| 16 | [Move Zeroes](./md_files/array/MoveZeroes.md) | 🟢 Easy | Fast and slow |
| 17 | [Subarray Sum Equals K](./md_files/array/SubarraySumEqualsK.md) | 🟡 Medium | Prefix sum + HashMap |
| 18 | [Set Matrix Zeroes](./md_files/matrix/SetMatrixZeroes.md) | 🟡 Medium | In-place matrix |
| 19 | [Rotate Image](./md_files/matrix/RotateImage.md) | 🟡 Medium | Matrix transpose |
| 20 | [Spiral Matrix](./md_files/matrix/SpiralMatrix.md) | 🟡 Medium | Boundary traversal |
| 21 | [Valid Sudoku](./md_files/matrix/ValidSudoku.md) | 🟡 Medium | Hash sets |
| 22 | [Two Sum II](./md_files/array/TwoSumII.md) | 🟡 Medium | Sorted two pointers |
| 23 | [3Sum](./md_files/array/ThreeSum.md) | 🟡 Medium | Sorting + pointers |
| 24 | [3Sum with Target (Indices, Hash Map)](./md_files/array/ThreeSumIndices.md) | 🟡 Medium | Fix one + Two Sum hash map |
| 25 | [4Sum](./md_files/array/FourSum.md) | 🟡 Medium | Multiple pointers |
| 26 | [Container With Most Water](./md_files/array/ContainerWithMostWater.md) | 🟡 Medium | Greedy pointers |
| 27 | [Trapping Rain Water](./md_files/array/TrappingRainWater.md) | 🔴 Hard | Two pointers |
| 28 | [Longest Substring Without Repeating Characters](./md_files/string/LongestSubString.md) | 🟡 Medium | Variable window |
| 29 | [Longest Repeating Character Replacement](./md_files/string/LongestRepeatingCharacterReplacement.md) | 🟡 Medium | Window + frequency |
| 30 | [Permutation in String](./md_files/string/PermutationInString.md) | 🟡 Medium | Fixed window |
| 31 | [Find All Anagrams in a String](./md_files/string/FindAllAnagramsInAString.md) | 🟡 Medium | Sliding window |
| 32 | [Minimum Window Substring](./md_files/string/MinimumWindowSubstring.md) | 🔴 Hard | Window + HashMap |
| 33 | [Sliding Window Maximum](./md_files/array/SlidingWindowMaximum.md) | 🔴 Hard | Monotonic deque |
| 34 | [Reverse a String](./md_files/string/ReverseString.md) | 🟢 Easy | Two pointers |
| 35 | [Palindrome Check (String and Number)](./md_files/string/PalindromeCheck.md) | 🟢 Easy | Two pointers |
| 36 | [Valid Palindrome](./md_files/string/ValidPalindrome.md) | 🟢 Easy | Two pointers |
| 37 | [Valid Palindrome II](./md_files/string/ValidPalindromeII.md) | 🟢 Easy | Conditional deletion |
| 38 | [Longest Common Prefix](./md_files/string/LongestCommonPrefix.md) | 🟢 Easy | String traversal |
| 39 | [Longest Palindromic Substring](./md_files/string/LongestPalindrome.md) | 🟡 Medium | Expand around center |
| 40 | [Palindromic Substrings](./md_files/string/PalindromicSubstrings.md) | 🟡 Medium | Expand around center |
| 41 | [String to Integer (atoi)](./md_files/string/StringToIntegerAtoi.md) | 🟡 Medium | Parsing |
| 42 | [Roman to Integer](./md_files/string/RomanToInteger.md) | 🟢 Easy | Mapping |
| 43 | [Integer to Roman](./md_files/string/IntegerToRoman.md) | 🟡 Medium | Greedy |
| 44 | [Multiply Strings](./md_files/string/MultiplyStrings.md) | 🟡 Medium | Manual arithmetic |
| 45 | [Encode and Decode Strings](./md_files/string/EncodeAndDecodeStrings.md) | 🟡 Medium | Serialization |
| 46 | [String Compression](./md_files/string/StringCompression.md) | 🟡 Medium | Two pointers |
| 47 | [Minimum Size Subarray Sum](./md_files/array/MinimumSizeSubarraySum.md) | 🟡 Medium | Variable-size sliding window |
| 48 | [Is Subsequence](./md_files/string/IsSubsequence.md) | 🟢 Easy | Two pointers |
| 49 | [ZigZag Conversion](./md_files/string/ZigZagConversion.md) | 🟡 Medium | Row simulation |
| 50 | [Text Justification](./md_files/string/TextJustification.md) | 🔴 Hard | Greedy line packing |
| 51 | [Palindrome Number](./md_files/string/PalindromeNumber.md) | 🟢 Easy | Reverse half of the number |
| 52 | [Reverse Words in a String](./md_files/string/ReverseWordsInAString.md) | 🟡 Medium | Split, trim and reverse |
| 53 | [Max Consecutive Ones III](./md_files/array/MaxConsecutiveOnesIII.md) | 🟡 Medium | Sliding window with a flip budget |
| 54 | [Subarray Product Less Than K](./md_files/array/SubarrayProductLessThanK.md) | 🟡 Medium | Sliding window with a product |
| 55 | [Pascal's Triangle](./md_files/array/PascalsTriangle.md) | 🟢 Easy | Build rows from the previous row |
| 56 | [Find Pivot Index](./md_files/array/FindPivotIndex.md) | 🟢 Easy | Prefix sum against the total |
| 57 | [Excel Sheet Column Title](./md_files/string/ExcelSheetColumnTitle.md) | 🟢 Easy | Bijective base-26 |
| 58 | [Squares of a Sorted Array](./md_files/array/SquaresOfASortedArray.md) | 🟢 Easy | Two pointers from both ends |
| 59 | [Count Binary Substrings](./md_files/string/CountBinarySubstrings.md) | 🟢 Easy | Run lengths of equal characters |
| 60 | [Reverse Vowels of a String](./md_files/string/ReverseVowelsOfAString.md) | 🟢 Easy | Two pointers |
| 61 | [Can Place Flowers](./md_files/array/CanPlaceFlowers.md) | 🟢 Easy | Greedy planting |
| 62 | [Rotate String](./md_files/string/RotateString.md) | 🟢 Easy | Substring of the doubled string |
| 63 | [Merge Strings Alternately](./md_files/string/MergeStringsAlternately.md) | 🟢 Easy | Two pointers |
| 64 | [Maximum Average Subarray I](./md_files/array/MaximumAverageSubarrayI.md) | 🟢 Easy | Fixed-size sliding window |
| 65 | [Minimum Value to Get Positive Step by Step Sum](./md_files/array/MinimumValueToGetPositiveStepByStepSum.md) | 🟢 Easy | Minimum prefix sum |
| 66 | [Minimum Absolute Difference](./md_files/array/MinimumAbsoluteDifference.md) | 🟢 Easy | Sort then compare neighbours |
| 67 | [Maximum Points You Can Obtain from Cards](./md_files/array/MaximumPointsYouCanObtainFromCards.md) | 🟡 Medium | Minimum sum window of size n - k |
| 68 | [Fruit Into Baskets](./md_files/array/FruitIntoBaskets.md) | 🟡 Medium | Longest window with at most two distinct values |
| 69 | [Remove Element](./md_files/array/RemoveElement.md) | 🟢 Easy | Overwrite with a write pointer |
| 70 | [Add Binary](./md_files/string/AddBinary.md) | 🟢 Easy | Digit-by-digit addition with carry |
| 71 | [Add Strings](./md_files/string/AddStrings.md) | 🟢 Easy | Digit-by-digit addition without big numbers |
| 72 | [Plus One](./md_files/array/PlusOne.md) | 🟢 Easy | Carry propagation from the right |
| 73 | [Find the Index of the First Occurrence in a String](./md_files/string/FirstOccurrenceInAString.md) | 🟢 Easy | Substring search |
| 74 | [Compare Version Numbers](./md_files/string/CompareVersionNumbers.md) | 🟡 Medium | Split and compare segment by segment |
| 75 | [Count and Say](./md_files/string/CountAndSay.md) | 🟡 Medium | Run-length description of the previous term |
| 76 | [Game of Life](./md_files/matrix/GameOfLife.md) | 🟡 Medium | In-place update with transition states |
| 77 | [Diagonal Traverse](./md_files/matrix/DiagonalTraverse.md) | 🟡 Medium | Walk anti-diagonals with alternating direction |
| 78 | [Remove All Occurrences of a Substring](./md_files/string/RemoveAllOccurrencesOfASubstring.md) | 🟡 Medium | Stack-like building with suffix check |
| 79 | [3Sum Closest](./md_files/array/ThreeSumClosest.md) | 🟡 Medium | Sort and two pointers |
| 80 | [Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit](./md_files/array/LongestContinuousSubarrayWithAbsoluteDiffLessThanOrEqualToLimit.md) | 🟡 Medium | Sliding window with two monotonic deques |
| 81 | [Maximum Sum Circular Subarray](./md_files/array/MaximumSumCircularSubarray.md) | 🟡 Medium | Kadane's algorithm for max and min |
| 82 | [Validate IP Address](./md_files/string/ValidateIPAddress.md) | 🟡 Medium | Parse and validate each group |
| 83 | [Integer to English Words](./md_files/string/IntegerToEnglishWords.md) | 🔴 Hard | Chunk by thousands |
| 84 | [Count Vowel Substrings of a String](./md_files/string/CountVowelSubstringsOfAString.md) | 🟢 Easy | Brute force over vowel-only windows |
| 85 | [Frequency of the Most Frequent Element](./md_files/array/FrequencyOfTheMostFrequentElement.md) | 🟡 Medium | Sorted sliding window with a budget |
| 86 | [Maximize Distance to Closest Person](./md_files/array/MaximizeDistanceToClosestPerson.md) | 🟡 Medium | Largest gap between occupied seats |
| 87 | [Sparse Matrix Multiplication](./md_files/matrix/SparseMatrixMultiplication.md) | 🟡 Medium | Skip zero entries |
| 88 | [Range Sum Query 2D - Immutable](./md_files/matrix/RangeSumQuery2DImmutable.md) | 🟡 Medium | 2D prefix sums |
| 89 | [Max Points on a Line](./md_files/array/MaxPointsOnALine.md) | 🔴 Hard | Slope map from each anchor point |
| 90 | [Subarrays with K Different Integers](./md_files/string/SubarraysWithKDifferentIntegers.md) | 🔴 Hard | At most K minus at most K minus 1 |
| 91 | [Candy Crush](./md_files/array/CandyCrush.md) | 🟡 Medium | Mark, crush and drop until stable |
| 92 | [Count Subarrays With Fixed Bounds](./md_files/array/CountSubarraysWithFixedBounds.md) | 🔴 Hard | Track the last min, max and out-of-range positions |
| 93 | [Substring with Concatenation of All Words](./md_files/string/SubstringWithConcatenationOfAllWords.md) | 🔴 Hard | Sliding window over word-sized steps |
| 94 | [Special Binary String](./md_files/string/SpecialBinaryString.md) | 🔴 Hard | Recursive decomposition into balanced primes |
| 95 | [Maximum Swap](./md_files/array/MaximumSwap.md) | 🟡 Medium | Last occurrence of each digit |
| 96 | [Check if Array Is Sorted and Rotated](./md_files/array/CheckIfArrayIsSortedAndRotated.md) | 🟢 Easy | Count descents on a circular array |
| 97 | [Shortest Palindrome](./md_files/string/ShortestPalindrome.md) | 🔴 Hard | Longest palindromic prefix with KMP prefix function |
| 98 | [Longest Substring with At Least K Repeating Characters](./md_files/string/LongestSubstringWithAtLeastKRepeatingCharacters.md) | 🟡 Medium | Divide and conquer on rare characters |
| 99 | [Remove Duplicates from Sorted Array II](./md_files/array/RemoveDuplicatesFromSortedArrayII.md) | 🟡 Medium | Write pointer compared with two positions back |
| 100 | [Count Number of Nice Subarrays](./md_files/array/CountNumberOfNiceSubarrays.md) | 🟡 Medium | Prefix count of odd numbers with a frequency map |
| 101 | [Summary Ranges](./md_files/array/SummaryRanges.md) | 🟢 Easy | Group consecutive runs |
| 102 | [Intersection of Two Arrays II](./md_files/array/IntersectionOfTwoArraysII.md) | 🟢 Easy | Frequency map of the smaller array |
| 103 | [Maximum Product of Three Numbers](./md_files/array/MaximumProductOfThreeNumbers.md) | 🟢 Easy | Two smallest and three largest |
| 104 | [Island Perimeter](./md_files/matrix/IslandPerimeter.md) | 🟢 Easy | Count land cells and shared edges |
| 105 | [Third Maximum Number](./md_files/array/ThirdMaximumNumber.md) | 🟢 Easy | Track three distinct maximums |
| 106 | [Restore the Array From Adjacent Pairs](./md_files/array/RestoreTheArrayFromAdjacentPairs.md) | 🟡 Medium | Adjacency map and walk from an endpoint |
| 107 | [Best Meeting Point](./md_files/array/BestMeetingPoint.md) | 🔴 Hard | Median of rows and columns |
| 108 | [Rotating the Box](./md_files/matrix/RotatingTheBox.md) | 🟡 Medium | Gravity per row, then rotate |
| 109 | [Shortest Word Distance](./md_files/array/ShortestWordDistance.md) | 🟢 Easy | Track the last index of each word |
| 110 | [Count Vowel Strings in Ranges](./md_files/array/CountVowelStringsInRanges.md) | 🟡 Medium | Prefix sums of qualifying words |
| 111 | [Rectangle Area](./md_files/array/RectangleArea.md) | 🟡 Medium | Sum of areas minus the overlap |
| 112 | [Longest Subarray of 1's After Deleting One Element](./md_files/array/LongestSubarrayOf1sAfterDeletingOneElement.md) | 🟡 Medium | Sliding window with at most one zero |
| 113 | [Grumpy Bookstore Owner](./md_files/array/GrumpyBookstoreOwner.md) | 🟡 Medium | Fixed window of the best extra satisfied customers |
| 114 | [Valid Triangle Number](./md_files/array/ValidTriangleNumber.md) | 🟡 Medium | Sort and two pointers |
| 115 | [Longest Mountain in Array](./md_files/array/LongestMountainInArray.md) | 🟡 Medium | Expand around each peak |

<a id="topic-02"></a>

### <img src="./images/topics/02-hashing.svg" width="32" height="32" align="absmiddle"> 02. Hashing

*HashMap, HashSet, frequency maps*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 116 | [Two Sum](./md_files/array/TwoSum.md) | 🟢 Easy | HashMap |
| 117 | [Contains Duplicate](./md_files/array/ContainsDuplicate.md) | 🟢 Easy | HashSet |
| 118 | [Intersection of Two Arrays](./md_files/array/IntersectionOfTwoArrays.md) | 🟢 Easy | HashSet |
| 119 | [Valid Anagram](./md_files/string/Anagram.md) | 🟢 Easy | Character frequency |
| 120 | [Group Anagrams](./md_files/string/GroupAnagrams.md) | 🟡 Medium | Frequency / Sorting |
| 121 | [Isomorphic Strings](./md_files/string/IsomorphicStrings.md) | 🟢 Easy | Two-way mapping |
| 122 | [First Non-Repeating Character](./md_files/string/FirstNonRepeatingCharacter.md) | 🟢 Easy | Frequency array |
| 123 | [Longest Consecutive Sequence](./md_files/array/LongestConsecutiveSequence.md) | 🟡 Medium | HashSet |
| 124 | [Design HashMap](./md_files/design/CustomHashMap.md) | 🟡 Medium | Hashing + collision handling |
| 125 | [Design HashSet](./md_files/design/DesignHashSet.md) | 🟢 Easy | Hashing |
| 126 | [Unique Email Addresses](./md_files/string/UniqueEmailAddresses.md) | 🟢 Easy | Normalise and hash |
| 127 | [Happy Number](./md_files/string/HappyNumber.md) | 🟢 Easy | Cycle detection on a number sequence |
| 128 | [Degree of an Array](./md_files/array/DegreeOfAnArray.md) | 🟢 Easy | Frequency map with first and last index |
| 129 | [Ransom Note](./md_files/string/RansomNote.md) | 🟢 Easy | Character counts |
| 130 | [Contiguous Array](./md_files/array/ContiguousArray.md) | 🟡 Medium | Prefix balance in a hash map |
| 131 | [Sort Characters By Frequency](./md_files/string/SortCharactersByFrequency.md) | 🟡 Medium | Frequency count and bucket sort |
| 132 | [Continuous Subarray Sum](./md_files/array/ContinuousSubarraySum.md) | 🟡 Medium | Prefix sums modulo k |
| 133 | [Minimum Number of Steps to Make Two Strings Anagram](./md_files/string/MinimumNumberOfStepsToMakeTwoStringsAnagram.md) | 🟡 Medium | Letter count difference |
| 134 | [Fraction to Recurring Decimal](./md_files/string/FractionToRecurringDecimal.md) | 🟡 Medium | Long division with remainder positions |
| 135 | [Subarray Sums Divisible by K](./md_files/array/SubarraySumsDivisibleByK.md) | 🟡 Medium | Prefix sum remainders |
| 136 | [Rabbits in Forest](./md_files/array/RabbitsInForest.md) | 🟡 Medium | Group rabbits that give the same answer |
| 137 | [Rank Teams by Votes](./md_files/array/RankTeamsByVotes.md) | 🟡 Medium | Position-count matrix and custom sort |
| 138 | [Word Pattern](./md_files/string/WordPattern.md) | 🟢 Easy | Two-way mapping between letters and words |
| 139 | [Verifying an Alien Dictionary](./md_files/string/VerifyingAnAlienDictionary.md) | 🟢 Easy | Letter rank lookup and adjacent comparison |

<a id="topic-03"></a>

### <img src="./images/topics/03-linked-lists.svg" width="32" height="32" align="absmiddle"> 03. Linked Lists

*Reversal, cycles, merge, pointers*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 140 | [Single Linked List](./md_files/linkedlist/SingleLinkedListDemo.md) | 🟢 Easy | Linked list implementation |
| 141 | [Reverse Linked List](./md_files/linkedlist/ReverseLinkedList.md) | 🟢 Easy | Iterative reversal |
| 142 | [Reverse Linked List II](./md_files/linkedlist/ReverseLinkedListII.md) | 🟡 Medium | Partial reversal |
| 143 | [Middle of the Linked List](./md_files/linkedlist/MiddleOfTheLinkedList.md) | 🟢 Easy | Fast and slow pointers |
| 144 | [Merge Two Sorted Lists](./md_files/linkedlist/MergeTwoSortedLists.md) | 🟢 Easy | Dummy node |
| 145 | [Linked List Cycle](./md_files/linkedlist/LinkedListCycle.md) | 🟢 Easy | Floyd's algorithm |
| 146 | [Linked List Cycle II](./md_files/linkedlist/LinkedListCycleII.md) | 🟡 Medium | Cycle entry detection |
| 147 | [Find the Duplicate Number](./md_files/linkedlist/FindTheDuplicateNumber.md) | 🟡 Medium | Floyd's cycle detection |
| 148 | [Add Two Numbers](./md_files/linkedlist/AddTwoNumbers.md) | 🟡 Medium | Carry handling |
| 149 | [Remove Nth Node From End](./md_files/linkedlist/RemoveNthNodeFromEnd.md) | 🟡 Medium | Two pointers |
| 150 | [Palindrome Linked List](./md_files/linkedlist/PalindromeLinkedList.md) | 🟢 Easy | Reverse second half |
| 151 | [Intersection of Two Linked Lists](./md_files/linkedlist/IntersectionOfTwoLinkedLists.md) | 🟢 Easy | Pointer switching |
| 152 | [Reorder List](./md_files/linkedlist/ReorderList.md) | 🟡 Medium | Split + reverse + merge |
| 153 | [Copy List with Random Pointer](./md_files/linkedlist/CopyListWithRandomPointer.md) | 🟡 Medium | HashMap / interweaving |
| 154 | [Sort List](./md_files/linkedlist/SortList.md) | 🟡 Medium | Merge sort |
| 155 | [Merge K Sorted Lists](./md_files/linkedlist/MergeKSortedLists.md) | 🔴 Hard | PriorityQueue |
| 156 | [Reverse Nodes in K-Group](./md_files/linkedlist/ReverseNodesInKGroup.md) | 🔴 Hard | Group reversal |
| 157 | [Remove Duplicates from Sorted List](./md_files/linkedlist/RemoveDuplicatesFromSortedList.md) | 🟢 Easy | Pointer skipping |
| 158 | [Remove Duplicates from Sorted List II](./md_files/linkedlist/RemoveDuplicatesFromSortedListII.md) | 🟡 Medium | Dummy head, skip runs |
| 159 | [Swap Nodes in Pairs](./md_files/linkedlist/SwapNodesInPairs.md) | 🟡 Medium | Pointer rewiring with a dummy head |
| 160 | [Odd Even Linked List](./md_files/linkedlist/OddEvenLinkedList.md) | 🟡 Medium | Two interleaved chains |
| 161 | [Rotate List](./md_files/linkedlist/RotateList.md) | 🟡 Medium | Make it a ring, cut at the new tail |
| 162 | [Convert Binary Number in a Linked List to Integer](./md_files/linkedlist/ConvertBinaryNumberInALinkedListToInteger.md) | 🟢 Easy | Shift and add while traversing |
| 163 | [Delete Node in a Linked List](./md_files/linkedlist/DeleteNodeInALinkedList.md) | 🟡 Medium | Copy the next node into this one |

<a id="topic-04"></a>

### <img src="./images/topics/04-stacks-queues.svg" width="32" height="32" align="absmiddle"> 04. Stacks & Queues

*Monotonic stack, deque, expressions*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 164 | [Valid Parentheses](./md_files/stack/ValidParentheses.md) | 🟢 Easy | Stack |
| 165 | [Min Stack](./md_files/stack/MinStack.md) | 🟡 Medium | Auxiliary stack |
| 166 | [Implement Queue Using Stacks](./md_files/stack/ImplementQueueUsingStacks.md) | 🟢 Easy | Two stacks |
| 167 | [Implement Stack Using Queues](./md_files/stack/ImplementStackUsingQueues.md) | 🟢 Easy | Queue |
| 168 | [Evaluate Reverse Polish Notation](./md_files/stack/EvaluateReversePolishNotation.md) | 🟡 Medium | Stack |
| 169 | [Decode String](./md_files/stack/DecodeString.md) | 🟡 Medium | Stack |
| 170 | [Minimum Remove to Make Valid Parentheses](./md_files/stack/MinimumRemoveToMakeValidParentheses.md) | 🟡 Medium | Stack |
| 171 | [Longest Valid Parentheses](./md_files/stack/LongestValidParentheses.md) | 🔴 Hard | Stack / DP |
| 172 | [Daily Temperatures](./md_files/stack/DailyTemperatures.md) | 🟡 Medium | Monotonic stack |
| 173 | [Next Greater Element I](./md_files/stack/NextGreaterElementI.md) | 🟢 Easy | Monotonic stack |
| 174 | [Next Greater Element II](./md_files/stack/NextGreaterElementII.md) | 🟡 Medium | Circular array |
| 175 | [Online Stock Span](./md_files/stack/OnlineStockSpan.md) | 🟡 Medium | Monotonic stack |
| 176 | [Asteroid Collision](./md_files/stack/AsteroidCollision.md) | 🟡 Medium | Stack simulation |
| 177 | [Car Fleet](./md_files/stack/CarFleet.md) | 🟡 Medium | Sorting + stack |
| 178 | [Largest Rectangle in Histogram](./md_files/stack/LargestRectangleInHistogram.md) | 🔴 Hard | Monotonic stack |
| 179 | [Basic Calculator](./md_files/stack/BasicCalculator.md) | 🔴 Hard | Stack / Parsing |
| 180 | [Remove K Digits](./md_files/stack/RemoveKDigits.md) | 🟡 Medium | Monotonic stack |
| 181 | [Simplify Path](./md_files/stack/SimplifyPath.md) | 🟡 Medium | Stack of path segments |
| 182 | [Backspace String Compare](./md_files/stack/BackspaceStringCompare.md) | 🟢 Easy | Backward scan with skip counts |
| 183 | [Remove All Adjacent Duplicates In String](./md_files/stack/RemoveAllAdjacentDuplicatesInString.md) | 🟢 Easy | Stack cancellation |
| 184 | [Minimum Number of Swaps to Make the String Balanced](./md_files/stack/MinimumNumberOfSwapsToMakeTheStringBalanced.md) | 🟡 Medium | Unmatched closing brackets |
| 185 | [Remove Duplicate Letters](./md_files/stack/RemoveDuplicateLetters.md) | 🟡 Medium | Monotonic stack with last occurrences |
| 186 | [Sum of Subarray Minimums](./md_files/stack/SumOfSubarrayMinimums.md) | 🟡 Medium | Monotonic stack, contribution of each element |
| 187 | [Number of Visible People in a Queue](./md_files/stack/NumberOfVisiblePeopleInAQueue.md) | 🔴 Hard | Monotonic decreasing stack from the right |
| 188 | [Exclusive Time of Functions](./md_files/stack/ExclusiveTimeOfFunctions.md) | 🟡 Medium | Call stack from log lines |
| 189 | [Remove All Adjacent Duplicates in String II](./md_files/stack/RemoveAllAdjacentDuplicatesInStringII.md) | 🟡 Medium | Stack of characters with run counts |
| 190 | [Smallest Subsequence of Distinct Characters](./md_files/stack/SmallestSubsequenceOfDistinctCharacters.md) | 🟡 Medium | Monotonic stack with last occurrences |
| 191 | [Reverse Substrings Between Each Pair of Parentheses](./md_files/stack/ReverseSubstringsBetweenEachPairOfParentheses.md) | 🟡 Medium | Stack of partial strings or paired-index walk |

<a id="topic-05"></a>

### <img src="./images/topics/05-binary-search.svg" width="32" height="32" align="absmiddle"> 05. Binary Search

*Search spaces, rotated arrays*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 192 | [Linear Search](./md_files/search/LinearSearch.md) | 🟢 Easy | Sequential scan |
| 193 | [Binary Search](./md_files/search/BinarySearch.md) | 🟢 Easy | Basic binary search |
| 194 | [Search Insert Position](./md_files/search/SearchInsertPosition.md) | 🟢 Easy | Lower bound |
| 195 | [First and Last Position of Element](./md_files/search/FirstAndLastPosition.md) | 🟡 Medium | Boundary search |
| 196 | [Search in Rotated Sorted Array](./md_files/search/SearchInRotatedSortedArray.md) | 🟡 Medium | Modified binary search |
| 197 | [Find Minimum in Rotated Sorted Array](./md_files/search/FindMinimumInRotatedSortedArray.md) | 🟡 Medium | Binary search |
| 198 | [Find Peak Element](./md_files/search/FindPeakElement.md) | 🟡 Medium | Binary search |
| 199 | [Search a 2D Matrix](./md_files/search/SearchA2DMatrix.md) | 🟡 Medium | Matrix binary search |
| 200 | [Koko Eating Bananas](./md_files/search/KokoEatingBananas.md) | 🟡 Medium | Binary search on answer |
| 201 | [Capacity to Ship Packages Within D Days](./md_files/search/CapacityToShipPackages.md) | 🟡 Medium | Feasibility search |
| 202 | [Median of Two Sorted Arrays](./md_files/search/MedianOfTwoSortedArrays.md) | 🔴 Hard | Partition-based search |
| 203 | [Split Array Largest Sum](./md_files/search/SplitArrayLargestSum.md) | 🔴 Hard | Binary search on the answer |
| 204 | [Sqrt(x)](./md_files/search/SqrtX.md) | 🟢 Easy | Binary search on the answer |
| 205 | [Single Element in a Sorted Array](./md_files/search/SingleElementInASortedArray.md) | 🟡 Medium | Binary search on pair alignment |
| 206 | [Heaters](./md_files/search/Heaters.md) | 🟡 Medium | Nearest heater with binary search |
| 207 | [Find the Smallest Divisor Given a Threshold](./md_files/search/SmallestDivisorGivenAThreshold.md) | 🟡 Medium | Binary search on the divisor |
| 208 | [Kth Missing Positive Number](./md_files/search/KthMissingPositiveNumber.md) | 🟢 Easy | Binary search on missing count |
| 209 | [Minimum Number of Days to Make m Bouquets](./md_files/search/MinimumNumberOfDaysToMakeMBouquets.md) | 🟡 Medium | Binary search on the answer |
| 210 | [Peak Index in a Mountain Array](./md_files/search/PeakIndexInAMountainArray.md) | 🟡 Medium | Binary search on the slope |

<a id="topic-06"></a>

### <img src="./images/topics/06-sorting-intervals.svg" width="32" height="32" align="absmiddle"> 06. Sorting & Intervals

*Merge intervals, custom sorting*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 211 | [Bubble Sort](./md_files/sort/BubbleSort.md) | 🟢 Easy | Adjacent swaps |
| 212 | [Selection Sort](./md_files/sort/SelectionSort.md) | 🟢 Easy | Minimum selection |
| 213 | [Insertion Sort](./md_files/sort/InsertionSort.md) | 🟢 Easy | Shifting |
| 214 | [Merge Sort](./md_files/sort/MergeSort.md) | 🟡 Medium | Divide and conquer |
| 215 | [Quick Sort](./md_files/sort/QuickSort.md) | 🟡 Medium | Partitioning |
| 216 | [Heap Sort](./md_files/sort/HeapSort.md) | 🟡 Medium | Binary heap |
| 217 | [Merge Intervals](./md_files/interval/MergeIntervals.md) | 🟡 Medium | Sorting |
| 218 | [Insert Interval](./md_files/interval/InsertInterval.md) | 🟡 Medium | Interval merging |
| 219 | [Non-overlapping Intervals](./md_files/interval/NonOverlappingIntervals.md) | 🟡 Medium | Greedy |
| 220 | [Meeting Rooms](./md_files/interval/MeetingRooms.md) | 🟢 Easy | Sorting |
| 221 | [Meeting Rooms II](./md_files/interval/MeetingRoomsII.md) | 🟡 Medium | Min heap |
| 222 | [Minimum Interval to Include Each Query](./md_files/interval/MinimumIntervalToIncludeEachQuery.md) | 🔴 Hard | Sorting + min heap |
| 223 | [Car Pooling](./md_files/array/CarPooling.md) | 🟡 Medium | Difference array of passenger changes |
| 224 | [Interval List Intersections](./md_files/interval/IntervalListIntersections.md) | 🟡 Medium | Two pointers over sorted interval lists |
| 225 | [Meeting Scheduler](./md_files/interval/MeetingScheduler.md) | 🟡 Medium | Two pointers over sorted availability |
| 226 | [Sort an Array](./md_files/sort/SortAnArray.md) | 🟡 Medium | Merge sort without built-in sorting |
| 227 | [Employee Free Time](./md_files/interval/EmployeeFreeTime.md) | 🔴 Hard | Merge all busy intervals, gaps are free time |
| 228 | [Make Lexicographically Smallest Array by Swapping Elements](./md_files/sort/MakeLexicographicallySmallestArrayBySwappingElements.md) | 🟡 Medium | Sort within groups of values at most limit apart |
| 229 | [Sort Colors](./md_files/sort/SortColors.md) | 🟡 Medium | Dutch national flag with three pointers |
| 230 | [Minimum Number of Arrows to Burst Balloons](./md_files/interval/MinimumNumberOfArrowsToBurstBalloons.md) | 🟡 Medium | Sort by end and shoot greedily |

<a id="topic-07"></a>

### <img src="./images/topics/07-trees-bst.svg" width="32" height="32" align="absmiddle"> 07. Trees & BST

*DFS, BFS, traversal, construction*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 231 | [Binary Tree Traversals (Inorder, Preorder, Postorder)](./md_files/tree/TreeTraversals.md) | 🟢 Easy | DFS orders |
| 232 | [Maximum Depth of Binary Tree](./md_files/tree/MaximumDepthOfBinaryTree.md) | 🟢 Easy | DFS |
| 233 | [Minimum Depth of Binary Tree](./md_files/tree/MinimumDepthOfBinaryTree.md) | 🟢 Easy | BFS |
| 234 | [Invert Binary Tree](./md_files/tree/InvertBinaryTree.md) | 🟢 Easy | Recursion |
| 235 | [Same Tree](./md_files/tree/SameTree.md) | 🟢 Easy | Tree comparison |
| 236 | [Symmetric Tree](./md_files/tree/SymmetricTree.md) | 🟢 Easy | Recursive comparison |
| 237 | [Subtree of Another Tree](./md_files/tree/SubtreeOfAnotherTree.md) | 🟢 Easy | Recursion |
| 238 | [Binary Tree Level Order Traversal](./md_files/tree/BinaryTreeLevelOrderTraversal.md) | 🟡 Medium | BFS |
| 239 | [Binary Tree Zigzag Level Order Traversal](./md_files/tree/BinaryTreeZigzagLevelOrder.md) | 🟡 Medium | BFS |
| 240 | [Binary Tree Right Side View](./md_files/tree/BinaryTreeRightSideView.md) | 🟡 Medium | BFS / DFS |
| 241 | [Vertical Order Traversal](./md_files/tree/VerticalOrderTraversal.md) | 🔴 Hard | BFS + Sorting |
| 242 | [Diameter of Binary Tree](./md_files/tree/DiameterOfBinaryTree.md) | 🟢 Easy | DFS |
| 243 | [Balanced Binary Tree](./md_files/tree/BalancedBinaryTree.md) | 🟢 Easy | Height calculation |
| 244 | [Count Good Nodes in Binary Tree](./md_files/tree/CountGoodNodesInBinaryTree.md) | 🟡 Medium | DFS with running maximum |
| 245 | [Lowest Common Ancestor](./md_files/tree/LowestCommonAncestorOfBinaryTree.md) | 🟡 Medium | Tree recursion |
| 246 | [Binary Search Tree](./md_files/tree/BinarySearchTree.md) | 🟡 Medium | BST implementation |
| 247 | [Validate Binary Search Tree](./md_files/tree/ValidateBinarySearchTree.md) | 🟡 Medium | Range validation |
| 248 | [Kth Smallest Element in BST](./md_files/tree/KthSmallestElementInBST.md) | 🟡 Medium | Inorder traversal |
| 249 | [Lowest Common Ancestor of a Binary Search Tree](./md_files/tree/LowestCommonAncestorOfBST.md) | 🟡 Medium | BST property |
| 250 | [Construct Tree from Preorder and Inorder](./md_files/tree/ConstructBinaryTreeFromPreorderInorder.md) | 🟡 Medium | Recursion + HashMap |
| 251 | [Path Sum II](./md_files/tree/PathSumII.md) | 🟡 Medium | Backtracking |
| 252 | [Flatten Binary Tree to Linked List](./md_files/tree/FlattenBinaryTreeToLinkedList.md) | 🟡 Medium | Tree transformation |
| 253 | [Serialize and Deserialize Binary Tree](./md_files/tree/SerializeDeserializeBinaryTree.md) | 🔴 Hard | BFS / DFS |
| 254 | [Binary Tree Maximum Path Sum](./md_files/tree/BinaryTreeMaximumPathSum.md) | 🔴 Hard | DFS |
| 255 | [Binary Tree Cameras](./md_files/tree/BinaryTreeCameras.md) | 🔴 Hard | Greedy / DFS |
| 256 | [Merge Two Binary Trees](./md_files/tree/MergeTwoBinaryTrees.md) | 🟢 Easy | Parallel DFS |
| 257 | [Convert Sorted Array to Binary Search Tree](./md_files/tree/ConvertSortedArrayToBST.md) | 🟢 Easy | Divide and conquer, midpoint root |
| 258 | [Path Sum](./md_files/tree/PathSum.md) | 🟢 Easy | DFS with a remaining target |
| 259 | [Split BST](./md_files/tree/SplitBST.md) | 🟡 Medium | Recursive split by value |
| 260 | [Delete Node in a BST](./md_files/tree/DeleteNodeInABST.md) | 🟡 Medium | Replace with the in-order successor |
| 261 | [Lowest Common Ancestor of a Binary Tree III](./md_files/tree/LowestCommonAncestorOfABinaryTreeIII.md) | 🟡 Medium | Parent pointers as intersecting lists |
| 262 | [Amount of Time for Binary Tree to Be Infected](./md_files/tree/AmountOfTimeForBinaryTreeToBeInfected.md) | 🟡 Medium | Parent map and BFS from the start node |
| 263 | [All Nodes Distance K in Binary Tree](./md_files/tree/AllNodesDistanceKInBinaryTree.md) | 🟡 Medium | Parent map and BFS to depth k |
| 264 | [Boundary of Binary Tree](./md_files/tree/BoundaryOfBinaryTree.md) | 🟡 Medium | Left boundary, leaves, reversed right boundary |
| 265 | [Binary Tree Vertical Order Traversal](./md_files/tree/BinaryTreeVerticalOrderTraversal.md) | 🟡 Medium | BFS with column indexes |
| 266 | [Path Sum III](./md_files/tree/PathSumIII.md) | 🟡 Medium | Prefix sums on a root-to-node path |
| 267 | [Binary Tree Paths](./md_files/tree/BinaryTreePaths.md) | 🟢 Easy | DFS building the path string |
| 268 | [Sum Root to Leaf Numbers](./md_files/tree/SumRootToLeafNumbers.md) | 🟡 Medium | DFS carrying the number so far |

<a id="topic-08"></a>

### <img src="./images/topics/08-heaps.svg" width="32" height="32" align="absmiddle"> 08. Heaps

*PriorityQueue, Top K, K-way merge*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 269 | [Kth Largest Element in a Stream](./md_files/heap/KthLargestElementInAStream.md) | 🟢 Easy | Min heap of size k |
| 270 | [Last Stone Weight](./md_files/heap/LastStoneWeight.md) | 🟢 Easy | Max heap |
| 271 | [Kth Largest Element in an Array](./md_files/heap/KthLargestElementInAnArray.md) | 🟡 Medium | Quickselect / Heap |
| 272 | [Top K Frequent Elements](./md_files/heap/TopKFrequentElements.md) | 🟡 Medium | HashMap + Heap |
| 273 | [K Closest Points to Origin](./md_files/heap/KClosestPointsToOrigin.md) | 🟡 Medium | PriorityQueue |
| 274 | [Find Median from Data Stream](./md_files/heap/FindMedianFromDataStream.md) | 🔴 Hard | Two heaps |
| 275 | [Task Scheduler](./md_files/heap/TaskScheduler.md) | 🟡 Medium | Heap + Greedy |
| 276 | [Reorganize String](./md_files/heap/ReorganizeString.md) | 🟡 Medium | Max heap |
| 277 | [Find K Pairs with Smallest Sums](./md_files/heap/FindKPairsWithSmallestSums.md) | 🟡 Medium | Heap |
| 278 | [Smallest Range Covering Elements from K Lists](./md_files/heap/SmallestRangeCoveringKLists.md) | 🔴 Hard | Heap |
| 279 | [Random Pick with Weight](./md_files/heap/RandomPickWithWeight.md) | 🟡 Medium | Prefix sums and binary search |
| 280 | [Top K Frequent Words](./md_files/heap/TopKFrequentWords.md) | 🟡 Medium | Min-heap with a tie-break |
| 281 | [Find K Closest Elements](./md_files/heap/FindKClosestElements.md) | 🟡 Medium | Binary search for the window start |
| 282 | [Kth Smallest Element in a Sorted Matrix](./md_files/heap/KthSmallestElementInASortedMatrix.md) | 🟡 Medium | Binary search on value or a min-heap of rows |
| 283 | [Furthest Building You Can Reach](./md_files/heap/FurthestBuildingYouCanReach.md) | 🟡 Medium | Min-heap of climbs covered by ladders |
| 284 | [The Skyline Problem](./md_files/heap/TheSkylineProblem.md) | 🔴 Hard | Sweep line with a max-heap of heights |
| 285 | [Maximum Product of Two Elements in an Array](./md_files/heap/MaximumProductOfTwoElementsInAnArray.md) | 🟢 Easy | Track the two largest values |

<a id="topic-09"></a>

### <img src="./images/topics/09-graphs.svg" width="32" height="32" align="absmiddle"> 09. Graphs

*BFS, DFS, Dijkstra, Union-Find*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 286 | [Graph Traversals (BFS and DFS)](./md_files/graph/GraphTraversals.md) | 🟢 Easy | BFS and DFS |
| 287 | [Flood Fill](./md_files/graph/FloodFill.md) | 🟢 Easy | DFS |
| 288 | [Number of Islands](./md_files/graph/NumberOfIslands.md) | 🟡 Medium | DFS / BFS |
| 289 | [Max Area of Island](./md_files/graph/MaxAreaOfIsland.md) | 🟡 Medium | DFS |
| 290 | [Surrounded Regions](./md_files/graph/SurroundedRegions.md) | 🟡 Medium | Border DFS |
| 291 | [Pacific Atlantic Water Flow](./md_files/graph/PacificAtlanticWaterFlow.md) | 🟡 Medium | Multi-source DFS |
| 292 | [Rotting Oranges](./md_files/graph/RottingOranges.md) | 🟡 Medium | Multi-source BFS |
| 293 | [Walls and Gates](./md_files/graph/WallsAndGates.md) | 🟡 Medium | Multi-source BFS |
| 294 | [Clone Graph](./md_files/graph/CloneGraph.md) | 🟡 Medium | DFS + HashMap |
| 295 | [Course Schedule](./md_files/graph/CourseSchedule.md) | 🟡 Medium | Cycle detection |
| 296 | [Word Ladder](./md_files/graph/WordLadder.md) | 🔴 Hard | BFS |
| 297 | [Evaluate Division](./md_files/graph/EvaluateDivision.md) | 🟡 Medium | Weighted graph |
| 298 | [Graph Valid Tree](./md_files/graph/GraphValidTree.md) | 🟡 Medium | DFS / Union-Find |
| 299 | [Number of Connected Components](./md_files/graph/NumberOfConnectedComponents.md) | 🟡 Medium | Union-Find |
| 300 | [Redundant Connection](./md_files/graph/RedundantConnection.md) | 🟡 Medium | DSU |
| 301 | [Accounts Merge](./md_files/graph/AccountsMerge.md) | 🟡 Medium | Union-Find |
| 302 | [Implement Union-Find from Scratch](./md_files/graph/UnionFind.md) | 🟡 Medium | Path compression + Union by Rank |
| 303 | [Network Delay Time](./md_files/graph/NetworkDelayTime.md) | 🟡 Medium | Dijkstra |
| 304 | [Cheapest Flights Within K Stops](./md_files/graph/CheapestFlightsWithinKStops.md) | 🟡 Medium | Bellman-Ford / BFS |
| 305 | [Implement Dijkstra from Scratch](./md_files/graph/DijkstraShortestPath.md) | 🟡 Medium | Graph + PriorityQueue |
| 306 | [Swim in Rising Water](./md_files/graph/SwimInRisingWater.md) | 🔴 Hard | Dijkstra / binary search |
| 307 | [Is Graph Bipartite?](./md_files/graph/IsGraphBipartite.md) | 🟡 Medium | Two-colouring with BFS |
| 308 | [Possible Bipartition](./md_files/graph/PossibleBipartition.md) | 🟡 Medium | Dislike graph two-colouring |
| 309 | [Shortest Path in Binary Matrix](./md_files/graph/ShortestPathInBinaryMatrix.md) | 🟡 Medium | BFS on a grid with 8 directions |
| 310 | [Open the Lock](./md_files/graph/OpenTheLock.md) | 🟡 Medium | BFS over lock states |
| 311 | [Number of Operations to Make Network Connected](./md_files/graph/NumberOfOperationsToMakeNetworkConnected.md) | 🟡 Medium | Union-Find components |
| 312 | [Number of Distinct Islands](./md_files/graph/NumberOfDistinctIslands.md) | 🟡 Medium | Shape signature of each island |
| 313 | [Bus Routes](./md_files/graph/BusRoutes.md) | 🔴 Hard | BFS over routes, not stops |
| 314 | [Making A Large Island](./md_files/graph/MakingALargeIsland.md) | 🔴 Hard | Label islands, then try flipping each zero |
| 315 | [Shortest Bridge](./md_files/graph/ShortestBridge.md) | 🟡 Medium | DFS to find one island, multi-source BFS to the other |
| 316 | [Minimum Knight Moves](./md_files/graph/MinimumKnightMoves.md) | 🟡 Medium | BFS on an infinite board with symmetry |
| 317 | [The Maze](./md_files/graph/TheMaze.md) | 🟡 Medium | BFS where the ball rolls until it hits a wall |
| 318 | [Shortest Distance from All Buildings](./md_files/graph/ShortestDistanceFromAllBuildings.md) | 🔴 Hard | BFS from every building, accumulate distances |
| 319 | [Word Ladder II](./md_files/graph/WordLadderII.md) | 🔴 Hard | BFS for levels, DFS to rebuild all shortest paths |
| 320 | [Snakes and Ladders](./md_files/graph/SnakesAndLadders.md) | 🟡 Medium | BFS over board squares with boustrophedon mapping |
| 321 | [Most Stones Removed with Same Row or Column](./md_files/graph/MostStonesRemovedWithSameRowOrColumn.md) | 🟡 Medium | Union-Find on rows and columns |
| 322 | [Number of Provinces](./md_files/graph/NumberOfProvinces.md) | 🟡 Medium | Union-Find on an adjacency matrix |

<a id="topic-10"></a>

### <img src="./images/topics/10-dynamic-programming.svg" width="32" height="32" align="absmiddle"> 10. Dynamic Programming

*Memoization, tabulation, optimization*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 323 | [Fibonacci](./md_files/dp/Fibonacci.md) | 🟢 Easy | Recursion / memoization |
| 324 | [Climbing Stairs](./md_files/dp/ClimbingStairs.md) | 🟢 Easy | 1D DP |
| 325 | [Min Cost Climbing Stairs](./md_files/dp/MinCostClimbingStairs.md) | 🟢 Easy | 1D DP |
| 326 | [House Robber](./md_files/dp/HouseRobber.md) | 🟡 Medium | DP |
| 327 | [House Robber II](./md_files/dp/HouseRobberII.md) | 🟡 Medium | Circular DP |
| 328 | [Decode Ways](./md_files/dp/DecodeWays.md) | 🟡 Medium | 1D DP |
| 329 | [Coin Change](./md_files/dp/CoinChange.md) | 🟡 Medium | Unbounded knapsack |
| 330 | [Word Break](./md_files/dp/WordBreak.md) | 🟡 Medium | DP + HashSet |
| 331 | [Longest Increasing Subsequence](./md_files/dp/LongestIncreasingSubsequence.md) | 🟡 Medium | DP / Binary search |
| 332 | [Unique Paths](./md_files/dp/UniquePaths.md) | 🟡 Medium | Grid DP |
| 333 | [Minimum Path Sum](./md_files/dp/MinimumPathSum.md) | 🟡 Medium | Grid DP |
| 334 | [Best Time to Buy and Sell Stock with Cooldown](./md_files/dp/BestTimeToBuyAndSellStockWithCooldown.md) | 🟡 Medium | State machine DP |
| 335 | [Longest Common Subsequence](./md_files/dp/LongestCommonSubsequence.md) | 🟡 Medium | 2D DP |
| 336 | [Longest Palindromic Subsequence](./md_files/dp/LongestPalindromicSubsequence.md) | 🟡 Medium | 2D DP |
| 337 | [Edit Distance](./md_files/dp/EditDistance.md) | 🟡 Medium | 2D DP |
| 338 | [Interleaving String](./md_files/dp/InterleavingString.md) | 🟡 Medium | 2D DP |
| 339 | [Paint Fence](./md_files/dp/PaintFence.md) | 🟡 Medium | Counting DP with two states |
| 340 | [Unique Paths II](./md_files/dp/UniquePathsII.md) | 🟡 Medium | Grid DP with obstacles |
| 341 | [K-th Symbol in Grammar](./md_files/dp/KthSymbolInGrammar.md) | 🟡 Medium | Recursion on a doubling structure |
| 342 | [Maximal Square](./md_files/dp/MaximalSquare.md) | 🟡 Medium | Grid DP, min of three neighbours |
| 343 | [Delete and Earn](./md_files/dp/DeleteAndEarn.md) | 🟡 Medium | Reduce to House Robber |
| 344 | [Perfect Squares](./md_files/dp/PerfectSquares.md) | 🟡 Medium | Unbounded knapsack over squares |
| 345 | [Triangle](./md_files/dp/TriangleMinimumPathSum.md) | 🟡 Medium | Bottom-up DP in place of the last row |
| 346 | [Find the Winner of the Circular Game](./md_files/dp/FindTheWinnerOfTheCircularGame.md) | 🟡 Medium | Josephus recurrence |
| 347 | [Minimum Cost For Tickets](./md_files/dp/MinimumCostForTickets.md) | 🟡 Medium | DP over days with 1, 7 and 30 day passes |
| 348 | [Frog Jump](./md_files/dp/FrogJump.md) | 🔴 Hard | DP on (stone, last jump size) |
| 349 | [Dungeon Game](./md_files/dp/DungeonGame.md) | 🔴 Hard | DP from the princess backward |
| 350 | [Longest String Chain](./md_files/dp/LongestStringChain.md) | 🟡 Medium | DP by removing one letter |

<a id="topic-11"></a>

### <img src="./images/topics/11-backtracking.svg" width="32" height="32" align="absmiddle"> 11. Backtracking

*Permutations, combinations, subsets*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 351 | [Subsets](./md_files/backtracking/Subsets.md) | 🟡 Medium | Decision tree |
| 352 | [Subsets II](./md_files/backtracking/SubsetsII.md) | 🟡 Medium | Duplicate handling |
| 353 | [Permutations](./md_files/backtracking/Permutations.md) | 🟡 Medium | Backtracking |
| 354 | [Permutations II](./md_files/backtracking/PermutationsII.md) | 🟡 Medium | Duplicate handling |
| 355 | [Combination Sum](./md_files/backtracking/CombinationSum.md) | 🟡 Medium | Reusable choices |
| 356 | [Combination Sum II](./md_files/backtracking/CombinationSumII.md) | 🟡 Medium | Single-use choices |
| 357 | [Letter Combinations of a Phone Number](./md_files/backtracking/LetterCombinationsOfAPhoneNumber.md) | 🟡 Medium | Backtracking |
| 358 | [Generate Parentheses](./md_files/backtracking/GenerateParentheses.md) | 🟡 Medium | Recursive construction |
| 359 | [Palindrome Partitioning](./md_files/backtracking/PalindromePartitioning.md) | 🟡 Medium | Backtracking + palindrome check |
| 360 | [Word Search](./md_files/backtracking/WordSearch.md) | 🟡 Medium | DFS + Backtracking |
| 361 | [N-Queens](./md_files/backtracking/NQueens.md) | 🔴 Hard | Constraint search |
| 362 | [Sudoku Solver](./md_files/backtracking/SudokuSolver.md) | 🔴 Hard | Constraint propagation |
| 363 | [Restore IP Addresses](./md_files/backtracking/RestoreIPAddresses.md) | 🟡 Medium | Backtracking over segment lengths |
| 364 | [Word Break II](./md_files/backtracking/WordBreakII.md) | 🔴 Hard | Backtracking with memoisation on the suffix |
| 365 | [Optimal Account Balancing](./md_files/backtracking/OptimalAccountBalancing.md) | 🔴 Hard | Backtracking over non-zero debts |
| 366 | [N-Queens II](./md_files/backtracking/NQueensII.md) | 🔴 Hard | Backtracking with column and diagonal bitmasks |
| 367 | [Permutation Sequence](./md_files/backtracking/PermutationSequence.md) | 🔴 Hard | Factorial number system |

<a id="topic-12"></a>

### <img src="./images/topics/12-greedy.svg" width="32" height="32" align="absmiddle"> 12. Greedy Algorithms

*Scheduling, selection, optimization*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 368 | [Jump Game](./md_files/greedy/JumpGame.md) | 🟡 Medium | Greedy |
| 369 | [Jump Game II](./md_files/greedy/JumpGameII.md) | 🟡 Medium | Greedy |
| 370 | [Gas Station](./md_files/greedy/GasStation.md) | 🟡 Medium | Greedy |
| 371 | [Candy](./md_files/greedy/Candy.md) | 🔴 Hard | Two-pass greedy |
| 372 | [Hand of Straights](./md_files/greedy/HandOfStraights.md) | 🟡 Medium | Greedy + TreeMap |
| 373 | [Merge Triplets to Form Target Triplet](./md_files/greedy/MergeTripletsToFormTargetTriplet.md) | 🟡 Medium | Greedy |
| 374 | [Partition Labels](./md_files/greedy/PartitionLabels.md) | 🟡 Medium | Last occurrence |
| 375 | [Valid Parenthesis String](./md_files/greedy/ValidParenthesisString.md) | 🟡 Medium | Greedy range |
| 376 | [Best Time to Buy and Sell Stock II](./md_files/greedy/BestTimeToBuyAndSellStockII.md) | 🟡 Medium | Sum of every upward move |
| 377 | [Largest Number](./md_files/greedy/LargestNumber.md) | 🟡 Medium | Custom comparator on concatenation |
| 378 | [Boats to Save People](./md_files/greedy/BoatsToSavePeople.md) | 🟡 Medium | Sort and pair heaviest with lightest |
| 379 | [Maximum Number of Events That Can Be Attended](./md_files/greedy/MaximumNumberOfEventsThatCanBeAttended.md) | 🟡 Medium | Greedy with a min-heap of end days |
| 380 | [Minimum Number of Taps to Open to Water a Garden](./md_files/greedy/MinimumNumberOfTapsToOpenToWaterAGarden.md) | 🔴 Hard | Jump game over tap ranges |
| 381 | [Put Marbles in Bags](./md_files/greedy/PutMarblesInBags.md) | 🔴 Hard | Sort adjacent pair sums |
| 382 | [Using a Robot to Print the Lexicographically Smallest String](./md_files/greedy/UsingARobotToPrintTheLexicographicallySmallestString.md) | 🟡 Medium | Stack and the smallest remaining letter |
| 383 | [Best Time to Buy and Sell Stock with Transaction Fee](./md_files/dp/BestTimeToBuyAndSellStockWithTransactionFee.md) | 🟡 Medium | Two-state DP: holding and cash |

<a id="topic-13"></a>

### <img src="./images/topics/13-tries.svg" width="32" height="32" align="absmiddle"> 13. Tries

*Prefix search, autocomplete*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 384 | [Implement Trie](./md_files/trie/ImplementTrie.md) | 🟡 Medium | Prefix tree |
| 385 | [Design Add and Search Words](./md_files/trie/DesignAddAndSearchWords.md) | 🟡 Medium | Trie + DFS |
| 386 | [Word Search II](./md_files/trie/WordSearchII.md) | 🔴 Hard | Trie + Backtracking |
| 387 | [Search Suggestions System](./md_files/trie/SearchSuggestionsSystem.md) | 🟡 Medium | Sorted products and binary search per prefix |
| 388 | [Design Search Autocomplete System](./md_files/trie/DesignSearchAutocompleteSystem.md) | 🔴 Hard | Trie of sentences with counts |

<a id="topic-14"></a>

### <img src="./images/topics/14-bit-manipulation.svg" width="32" height="32" align="absmiddle"> 14. Bit Manipulation

*XOR, masks, swapping, bit operations*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 389 | [Single Number](./md_files/bit/SingleNumber.md) | 🟢 Easy | XOR |
| 390 | [Single Number II](./md_files/bit/SingleNumberII.md) | 🟡 Medium | Bit manipulation |
| 391 | [Counting Bits](./md_files/bit/CountingBits.md) | 🟢 Easy | DP + Bits |
| 392 | [Reverse Bits](./md_files/bit/ReverseBits.md) | 🟢 Easy | Bit operations |
| 393 | [Number of 1 Bits](./md_files/bit/NumberOf1Bits.md) | 🟢 Easy | Brian Kernighan |
| 394 | [Swap Two Numbers Without a Temporary Variable](./md_files/bit/SwapTwoNumbers.md) | 🟢 Easy | XOR / arithmetic |
| 395 | [Pow(x, n)](./md_files/bit/PowXN.md) | 🟡 Medium | Fast exponentiation by squaring |
| 396 | [Power of Two](./md_files/bit/PowerOfTwo.md) | 🟢 Easy | One set bit check |
| 397 | [Sum of Two Integers](./md_files/bit/SumOfTwoIntegers.md) | 🟡 Medium | XOR for the sum and AND for the carry |

<a id="topic-15"></a>

### <img src="./images/topics/15-advanced-graphs.svg" width="32" height="32" align="absmiddle"> 15. Advanced Graphs

*Topological sort, MST, SCC*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 398 | [Course Schedule II](./md_files/graph/CourseScheduleII.md) | 🟡 Medium | Topological sort |
| 399 | [Topological Sort](./md_files/graph/TopologicalSort.md) | 🟡 Medium | Kahn's algorithm / DFS |
| 400 | [Alien Dictionary](./md_files/graph/AlienDictionary.md) | 🔴 Hard | Topological sort |
| 401 | [Minimum Spanning Tree](./md_files/graph/MinimumSpanningTree.md) | 🟡 Medium | Kruskal / Prim |
| 402 | [Min Cost to Connect All Points](./md_files/graph/MinCostToConnectAllPoints.md) | 🟡 Medium | Prim / Kruskal |
| 403 | [Critical Connections in a Network](./md_files/graph/CriticalConnections.md) | 🔴 Hard | Tarjan's algorithm |
| 404 | [Reconstruct Itinerary](./md_files/graph/ReconstructItinerary.md) | 🔴 Hard | Eulerian path (Hierholzer) |
| 405 | [Shortest Path in a Grid with Obstacles Elimination](./md_files/graph/ShortestPathInAGridWithObstaclesElimination.md) | 🔴 Hard | BFS with state (row, col, eliminations left) |
| 406 | [Parallel Courses III](./md_files/graph/ParallelCoursesIII.md) | 🔴 Hard | Topological order with longest path |
| 407 | [Minimize Malware Spread](./md_files/graph/MinimizeMalwareSpread.md) | 🔴 Hard | Union-Find components with initial infections |
| 408 | [Minimum Edge Reversals So Every Node Is Reachable](./md_files/graph/MinimumEdgeReversalsSoEveryNodeIsReachable.md) | 🔴 Hard | Rerooting on a tree with reversal costs |
| 409 | [Collect Coins in a Tree](./md_files/graph/CollectCoinsInATree.md) | 🔴 Hard | Prune leaves, then peel two layers |

<a id="topic-16"></a>

### <img src="./images/topics/16-advanced-dp.svg" width="32" height="32" align="absmiddle"> 16. Advanced DP

*Knapsack, interval DP, tree DP*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 410 | [Coin Change II](./md_files/dp/CoinChangeII.md) | 🟡 Medium | Combination DP |
| 411 | [Partition Equal Subset Sum](./md_files/dp/PartitionEqualSubsetSum.md) | 🟡 Medium | Knapsack |
| 412 | [Target Sum](./md_files/dp/TargetSum.md) | 🟡 Medium | Knapsack transformation |
| 413 | [0/1 Knapsack](./md_files/dp/Knapsack01.md) | 🟡 Medium | 0/1 knapsack |
| 414 | [Distinct Subsequences](./md_files/dp/DistinctSubsequences.md) | 🔴 Hard | 2D DP |
| 415 | [Regular Expression Matching](./md_files/dp/RegularExpressionMatching.md) | 🔴 Hard | 2D DP |
| 416 | [Burst Balloons](./md_files/dp/BurstBalloons.md) | 🔴 Hard | Interval DP |
| 417 | [Longest Increasing Path in a Matrix](./md_files/dp/LongestIncreasingPathInAMatrix.md) | 🔴 Hard | DFS + Memoization |
| 418 | [Maximum Profit in Job Scheduling](./md_files/dp/MaximumProfitInJobScheduling.md) | 🔴 Hard | DP + Binary search |
| 419 | [Russian Doll Envelopes](./md_files/dp/RussianDollEnvelopes.md) | 🔴 Hard | Sort then longest increasing subsequence |
| 420 | [Wildcard Matching](./md_files/dp/WildcardMatching.md) | 🔴 Hard | Two pointers with star backtracking, or 2D DP |
| 421 | [Maximal Rectangle](./md_files/dp/MaximalRectangle.md) | 🔴 Hard | Histogram of heights per row |
| 422 | [Remove Boxes](./md_files/dp/RemoveBoxes.md) | 🔴 Hard | Interval DP with extra equal boxes attached |
| 423 | [Cherry Pickup](./md_files/dp/CherryPickup.md) | 🔴 Hard | Two walkers moving together |
| 424 | [Count Palindromic Subsequences](./md_files/dp/CountPalindromicSubsequences.md) | 🔴 Hard | Prefix and suffix pair counts around a middle digit |

<a id="topic-17"></a>

### <img src="./images/topics/17-design-problems.svg" width="32" height="32" align="absmiddle"> 17. Design Problems

*LRU, LFU, data structures*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 425 | [LRU Cache](./md_files/design/LRUCache.md) | 🟡 Medium | HashMap + Doubly Linked List |
| 426 | [LFU Cache](./md_files/design/LFUCache.md) | 🔴 Hard | HashMap + Frequency Lists |
| 427 | [Design Hit Counter](./md_files/design/DesignHitCounter.md) | 🟡 Medium | Queue + Sliding window |
| 428 | [Logger Rate Limiter](./md_files/design/LoggerRateLimiter.md) | 🟢 Easy | HashMap + Time |
| 429 | [Time Based Key-Value Store](./md_files/design/TimeBasedKeyValueStore.md) | 🟡 Medium | HashMap + Binary search |
| 430 | [Design Twitter](./md_files/design/DesignTwitter.md) | 🟡 Medium | Heap + HashMap |
| 431 | [Insert Delete GetRandom O(1)](./md_files/design/InsertDeleteGetRandom.md) | 🟡 Medium | ArrayList + HashMap |
| 432 | [All O(1) Data Structure](./md_files/design/AllOneDataStructure.md) | 🔴 Hard | Doubly linked list + HashMap |
| 433 | [Design Circular Queue](./md_files/design/DesignCircularQueue.md) | 🟡 Medium | Array / Circular buffer |
| 434 | [Design Skip List](./md_files/design/DesignSkipList.md) | 🔴 Hard | Probabilistic data structure |
| 435 | [Design a Min/Max Heap](./md_files/design/BinaryHeap.md) | 🟡 Medium | Heap implementation |
| 436 | [Design Browser History](./md_files/design/DesignBrowserHistory.md) | 🟡 Medium | Array with a cursor, truncate on visit |
| 437 | [Design File System](./md_files/design/DesignFileSystem.md) | 🟡 Medium | HashMap of full paths |
| 438 | [Snapshot Array](./md_files/design/SnapshotArray.md) | 🟡 Medium | Per-index history with binary search |
| 439 | [Simple Bank System](./md_files/design/SimpleBankSystem.md) | 🟡 Medium | Array of balances with validation |
| 440 | [Design Memory Allocator](./md_files/design/DesignMemoryAllocator.md) | 🟡 Medium | Scan for the first free block |
| 441 | [Design Tic-Tac-Toe](./md_files/design/DesignTicTacToe.md) | 🟡 Medium | Row, column and diagonal counters |
| 442 | [Maximum Frequency Stack](./md_files/design/MaximumFrequencyStack.md) | 🔴 Hard | Stack per frequency |
| 443 | [Design Authentication Manager](./md_files/design/DesignAuthenticationManager.md) | 🟡 Medium | Map of token to expiry time |
| 444 | [Dot Product of Two Sparse Vectors](./md_files/design/DotProductOfTwoSparseVectors.md) | 🟡 Medium | Store only non-zero entries |
| 445 | [Flatten Nested List Iterator](./md_files/design/FlattenNestedListIterator.md) | 🟡 Medium | Stack of iterators |
| 446 | [Design a Text Editor](./md_files/design/DesignTextEditor.md) | 🔴 Hard | Two stacks around the cursor |
| 447 | [Design In-Memory File System](./md_files/design/DesignInMemoryFileSystem.md) | 🔴 Hard | Trie of directories and files |
| 448 | [Block Placement Queries](./md_files/design/BlockPlacementQueries.md) | 🔴 Hard | Obstacles in a sorted set and a segment tree of gaps |
| 449 | [Moving Average from Data Stream](./md_files/design/MovingAverageFromDataStream.md) | 🟢 Easy | Fixed-size queue with a running sum |
| 450 | [Product of the Last K Numbers](./md_files/design/ProductOfTheLastKNumbers.md) | 🟡 Medium | Prefix products reset on zero |

<a id="topic-18"></a>

### <img src="./images/topics/18-java-core-concurrency.svg" width="32" height="32" align="absmiddle"> 18. Java Core & Concurrency

*Core Java basics, threads, locks, streams*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 451 | [Factorial](./md_files/core/Factorial.md) | 🟢 Easy | Recursion / iteration |
| 452 | [Prime Numbers](./md_files/core/PrimeNumbers.md) | 🟢 Easy | Sieve of Eratosthenes |
| 453 | [GCD and LCM](./md_files/core/GcdAndLcm.md) | 🟢 Easy | Euclid's algorithm |
| 454 | [Armstrong Number](./md_files/core/ArmstrongNumber.md) | 🟢 Easy | Digit math |
| 455 | [FizzBuzz](./md_files/core/FizzBuzz.md) | 🟢 Easy | Modulo |
| 456 | [Producer-Consumer Implementation](./md_files/threads/ProducerConsumer.md) | 🟡 Medium | Raw monitor synchronization (wait() / notifyAll()) |
| 457 | [Print Odd and Even Alternately](./md_files/threads/PrintEvenOddUsingThreads.md) | 🟡 Medium | Thread coordination using wait/notify or semaphores |
| 458 | [Print Numbers in Sequence with Three Threads](./md_files/threads/PrintNumbersWithThreeThreads.md) | 🟡 Medium | wait / notifyAll |
| 459 | [Concurrency Coordination Puzzles](./md_files/threads/ConcurrencyCoordinationPuzzles.md) | 🟡 Medium | Print in Order / FooBar sequence synchronization |
| 460 | [Thread-Safe BlockingQueue](./md_files/threads/BoundedBlockingQueue.md) | 🔴 Hard | ReentrantLock + Conditions (notFull, notEmpty) |
| 461 | [Custom Simple Thread Pool](./md_files/threads/SimpleThreadPool.md) | 🔴 Hard | Worker thread pool with a task queue |
| 462 | [Custom Read-Write Lock](./md_files/threads/CustomReadWriteLock.md) | 🔴 Hard | State-based tracking for multiple readers / single writer |
| 463 | [Deadlock and How to Avoid It](./md_files/threads/DeadlockDemo.md) | 🟡 Medium | Lock ordering |
| 464 | [Thread-Safe Singleton Variations](./md_files/core/SingletonPattern.md) | 🟡 Medium | Double-Checked Locking (volatile) and Bill Pugh Holder pattern |
| 465 | [Fully Immutable Class with Mutable Fields](./md_files/core/ImmutableClassDemo.md) | 🟡 Medium | Deep defensive copying for constructors and getters |
| 466 | [Custom Functional Interface and Collector](./md_files/core/CustomCollectors.md) | 🔴 Hard | Custom stream reduction aggregation logic |
| 467 | [Custom Resource Manager (AutoCloseable)](./md_files/core/ResourceManager.md) | 🟡 Medium | Try-with-resources integration with exception suppression |
| 468 | [Java 8 Streams Interview Questions](./md_files/core/StreamsInterviewQuestions.md) | 🟡 Medium | Java 8 streams |
| 469 | [Reverse Integer](./md_files/core/ReverseInteger.md) | 🟡 Medium | Digit pop and push with overflow check |
| 470 | [Count Primes](./md_files/core/CountPrimes.md) | 🟡 Medium | Sieve of Eratosthenes |
| 471 | [Angle Between Hands of a Clock](./md_files/core/AngleBetweenHandsOfAClock.md) | 🟡 Medium | Degrees per hour and per minute |
| 472 | [Reaching Points](./md_files/core/ReachingPoints.md) | 🔴 Hard | Work backward with the Euclidean algorithm |
| 473 | [Add Digits](./md_files/core/AddDigits.md) | 🟢 Easy | Digital root formula |

## Pattern Cheat Sheet 💡

Most interview problems are one of a handful of patterns in disguise. Spot the signal in the statement, then pick the tool.

| If the problem says... | Reach for | Examples in this repo |
|------------------------|-----------|-----------------------|
| sorted array, pair or triplet with a target | Two pointers or binary search | [3Sum](./md_files/array/ThreeSum.md), [Container With Most Water](./md_files/array/ContainerWithMostWater.md) |
| longest or shortest subarray or substring with a condition | Sliding window | [Longest Substring Without Repeating Characters](./md_files/string/LongestSubString.md), [Subarrays with K Different Integers](./md_files/string/SubarraysWithKDifferentIntegers.md) |
| count or find a subarray with a given sum | Prefix sum plus HashMap | [Subarray Sum Equals K](./md_files/array/SubarraySumEqualsK.md) |
| top K, k-th largest, running median | Heap | [Top K Frequent Words](./md_files/heap/TopKFrequentWords.md), [The Skyline Problem](./md_files/heap/TheSkylineProblem.md) |
| next greater or smaller element, nested structure | Monotonic stack | [Maximal Rectangle](./md_files/dp/MaximalRectangle.md), [Remove K Digits](./md_files/stack/RemoveKDigits.md) |
| shortest path with equal edge costs, level by level | BFS | [Word Ladder II](./md_files/graph/WordLadderII.md), [Snakes and Ladders](./md_files/graph/SnakesAndLadders.md) |
| connected components, cycles, ordering with dependencies | DFS, Union-Find, topological sort | [Course Schedule](./md_files/graph/CourseSchedule.md), [Parallel Courses III](./md_files/graph/ParallelCoursesIII.md) |
| all combinations, permutations or placements | Backtracking | [Word Break II](./md_files/backtracking/WordBreakII.md), [Optimal Account Balancing](./md_files/backtracking/OptimalAccountBalancing.md) |
| minimum, maximum or count of ways, overlapping subproblems | Dynamic programming | [Cherry Pickup](./md_files/dp/CherryPickup.md), [Remove Boxes](./md_files/dp/RemoveBoxes.md) |
| locally best choice looks safe | Greedy | [Put Marbles in Bags](./md_files/greedy/PutMarblesInBags.md) |
| prefix search, many strings | Trie | [Search Suggestions System](./md_files/trie/SearchSuggestionsSystem.md) |
| intervals, meetings, schedules | Sort by start, sweep line | [The Skyline Problem](./md_files/heap/TheSkylineProblem.md) |
| answer is monotonic in a parameter | Binary search on the answer | [Split Array Largest Sum](./md_files/search/SplitArrayLargestSum.md) |
| build a data structure with O(1) operations | HashMap plus linked list or array | [LRU Cache](./md_files/design/LRUCache.md), [Design a Text Editor](./md_files/design/DesignTextEditor.md) |

## Suggested Study Plan 🗓️

Work in the topic order of the tables above, since later topics lean on earlier ones.

| Week | Topics | Goal |
|------|--------|------|
| Week 1 | 01 Arrays & Strings, 02 Hashing | Two pointers, sliding window and prefix sums without looking anything up |
| Week 2 | 03 Linked Lists, 04 Stacks & Queues, 05 Binary Search | Pointer manipulation and monotonic stacks |
| Week 3 | 06 Sorting & Intervals, 07 Trees & BST, 08 Heaps | Recursion on trees, top-K patterns |
| Week 4 | 09 Graphs, 13 Tries | BFS, DFS, topological sort |
| Week 5 | 10 Dynamic Programming, 11 Backtracking, 12 Greedy | Choose between DP, backtracking and greedy for a new problem |
| Week 6 | 14 Bit Manipulation, 15 Advanced Graphs, 16 Advanced DP | Hard problems, Dijkstra, interval DP |
| Week 7 | 17 Design Problems, 18 Java Core & Concurrency | LRU-style designs, locks, thread pools |

For each problem: read the statement, try it on your own for 20 to 30 minutes, then open the page and compare your approach with the brute force and optimized ones.

## Repository Structure 🧱

```text
JavaInterviewPrograms/
├── pom.xml                      # Java 8 build, no dependencies
├── README.md                    # this index
├── images/                      # banner and topic icons
├── src/main/java/com/ranga/
│   ├── array/  string/  matrix/ # one package per topic area
│   ├── linkedlist/  stack/  tree/  graph/  heap/  trie/
│   ├── dp/  backtracking/  greedy/  search/  sort/  interval/
│   ├── bit/  design/  core/  threads/
│   └── ...                      # each program is one standalone class
└── md_files/<topic>/<Class>.md  # the write-up for each class, same name
```

Every page in `md_files` has a class with the same name in the matching package.

## How to Run 🚀

You need JDK 8 or newer and Maven.

```bash
git clone https://github.com/rangareddy/JavaInterviewPrograms.git
cd JavaInterviewPrograms
mvn compile
java -cp target/classes com.ranga.array.MaximumSubarray
```

Replace the class name with any program in the tables; the package is the topic folder shown in its link.

## Contributing 🤝

Found a bug, a faster solution or a missing problem? Open an issue or a pull request. Please keep to the layout above: a Javadoc'd standalone class, an aligned `Input  :` and `Output :` in `main`, a page with tested approaches, and one README row. Check the tables first so a problem is not listed twice.

Happy coding and good luck with your interviews! 🎉
