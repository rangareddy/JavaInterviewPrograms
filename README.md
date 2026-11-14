# Java Interview Programs

!["Java Interview Programs"](./images/Java_Interview_Programs.png)

## Coding Interview Questions by Topic

Interview problems grouped by topic, in a recommended study order. Each page explains the strategy, shows several approaches from brute force to optimized, states the time and space complexity, and includes the Java source. Every program appears exactly once, under the topic where it is best learned.

### 1. Complete topic coverage

21 topics, 260 programs.

<table>
<tr><td width="50%" valign="top"><code>01</code> <b><a href="#01-arrays--strings">Arrays &amp; Strings</a></b><br><sub>Two pointers, prefix sums, sliding window</sub><br><sub>46 problems</sub></td><td width="50%" valign="top"><code>02</code> <b><a href="#02-hashing">Hashing</a></b><br><sub>HashMap, HashSet, frequency maps</sub><br><sub>10 problems</sub></td></tr>
<tr><td width="50%" valign="top"><code>03</code> <b><a href="#03-linked-lists">Linked Lists</a></b><br><sub>Reversal, cycles, merge, pointers</sub><br><sub>17 problems</sub></td><td width="50%" valign="top"><code>04</code> <b><a href="#04-stacks--queues">Stacks &amp; Queues</a></b><br><sub>Monotonic stack, deque, expressions</sub><br><sub>16 problems</sub></td></tr>
<tr><td width="50%" valign="top"><code>05</code> <b><a href="#05-binary-search">Binary Search</a></b><br><sub>Search spaces, rotated arrays</sub><br><sub>11 problems</sub></td><td width="50%" valign="top"><code>06</code> <b><a href="#06-sorting--intervals">Sorting &amp; Intervals</a></b><br><sub>Merge intervals, custom sorting</sub><br><sub>13 problems</sub></td></tr>
<tr><td width="50%" valign="top"><code>07</code> <b><a href="#07-trees--bst">Trees &amp; BST</a></b><br><sub>DFS, BFS, traversal, construction</sub><br><sub>25 problems</sub></td><td width="50%" valign="top"><code>08</code> <b><a href="#08-heaps">Heaps</a></b><br><sub>PriorityQueue, Top K, K-way merge</sub><br><sub>10 problems</sub></td></tr>
<tr><td width="50%" valign="top"><code>09</code> <b><a href="#09-graphs">Graphs</a></b><br><sub>BFS, DFS, Dijkstra, Union-Find</sub><br><sub>21 problems</sub></td><td width="50%" valign="top"><code>10</code> <b><a href="#10-dynamic-programming">Dynamic Programming</a></b><br><sub>Memoization, tabulation, optimization</sub><br><sub>15 problems</sub></td></tr>
<tr><td width="50%" valign="top"><code>11</code> <b><a href="#11-backtracking">Backtracking</a></b><br><sub>Permutations, combinations, subsets</sub><br><sub>12 problems</sub></td><td width="50%" valign="top"><code>12</code> <b><a href="#12-greedy-algorithms">Greedy Algorithms</a></b><br><sub>Scheduling, selection, optimization</sub><br><sub>8 problems</sub></td></tr>
<tr><td width="50%" valign="top"><code>13</code> <b><a href="#13-tries">Tries</a></b><br><sub>Prefix search, autocomplete</sub><br><sub>3 problems</sub></td><td width="50%" valign="top"><code>14</code> <b><a href="#14-bit-manipulation">Bit Manipulation</a></b><br><sub>XOR, masks, bit operations</sub><br><sub>5 problems</sub></td></tr>
<tr><td width="50%" valign="top"><code>15</code> <b><a href="#15-recursion">Recursion</a></b><br><sub>Recurrence, recursion trees</sub><br><sub>2 problems</sub></td><td width="50%" valign="top"><code>16</code> <b><a href="#16-advanced-graphs">Advanced Graphs</a></b><br><sub>Topological sort, MST, SCC</sub><br><sub>7 problems</sub></td></tr>
<tr><td width="50%" valign="top"><code>17</code> <b><a href="#17-advanced-dp">Advanced DP</a></b><br><sub>Knapsack, interval DP, tree DP</sub><br><sub>9 problems</sub></td><td width="50%" valign="top"><code>18</code> <b><a href="#18-math--geometry">Math &amp; Geometry</a></b><br><sub>GCD, primes, coordinates</sub><br><sub>5 problems</sub></td></tr>
<tr><td width="50%" valign="top"><code>19</code> <b><a href="#19-design-problems">Design Problems</a></b><br><sub>LRU, LFU, data structures</sub><br><sub>11 problems</sub></td><td width="50%" valign="top"><code>20</code> <b><a href="#20-advanced-algorithms">Advanced Algorithms</a></b><br><sub>KMP, Rabin-Karp, segment trees</sub><br><sub>1 problem</sub></td></tr>
<tr><td width="50%" valign="top"><code>21</code> <b><a href="#21-java-core--concurrency">Java Core &amp; Concurrency</a></b><br><sub>Threads, locks, immutability, streams</sub><br><sub>13 problems</sub></td><td></td></tr>
</table>

### 01. Arrays & Strings

*Two pointers, prefix sums, sliding window*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 1 | [Second Largest Element in an Array](./md_files/array/SecondLargestElement.md) | Easy | Single pass |
| 2 | [Best Time to Buy and Sell Stock](./md_files/array/BestTimeToBuyAndSellStock.md) | Easy | Greedy |
| 3 | [Maximum Subarray](./md_files/array/MaximumSubarray.md) | Medium | Kadane's algorithm |
| 4 | [Product of Array Except Self](./md_files/array/ProductOfArrayExceptSelf.md) | Medium | Prefix/suffix |
| 5 | [Maximum Product Subarray](./md_files/array/MaximumProductSubarray.md) | Medium | Dynamic tracking |
| 6 | [Majority Element](./md_files/array/MajorityElement.md) | Easy | Boyer-Moore |
| 7 | [Majority Element II](./md_files/array/MajorityElementII.md) | Medium | Extended Boyer-Moore |
| 8 | [Missing Number](./md_files/array/MissingNumber.md) | Easy | XOR / Math |
| 9 | [Find All Numbers Disappeared in an Array](./md_files/array/FindAllNumbersDisappeared.md) | Easy | In-place marking |
| 10 | [First Missing Positive](./md_files/array/FirstMissingPositive.md) | Hard | Index placement |
| 11 | [Merge Sorted Array](./md_files/array/MergeSortedArray.md) | Easy | Two pointers |
| 12 | [Rotate an Array](./md_files/array/RotateAnArray.md) | Medium | Reversal |
| 13 | [Next Permutation](./md_files/array/NextPermutation.md) | Medium | Lexicographic order |
| 14 | [Sort Colors](./md_files/array/MoveZerosAndOnes.md) | Medium | Dutch National Flag |
| 15 | [Remove Duplicates from Sorted Array](./md_files/array/RemoveDuplicates.md) | Easy | In-place pointers |
| 16 | [Move Zeroes](./md_files/array/MoveZeroes.md) | Easy | Fast and slow |
| 17 | [Subarray Sum Equals K](./md_files/array/SubarraySumEqualsK.md) | Medium | Prefix sum + HashMap |
| 18 | [Set Matrix Zeroes](./md_files/matrix/SetMatrixZeroes.md) | Medium | In-place matrix |
| 19 | [Rotate Image](./md_files/matrix/RotateImage.md) | Medium | Matrix transpose |
| 20 | [Spiral Matrix](./md_files/matrix/SpiralMatrix.md) | Medium | Boundary traversal |
| 21 | [Valid Sudoku](./md_files/matrix/ValidSudoku.md) | Medium | Hash sets |
| 22 | [Two Sum II](./md_files/array/TwoSumII.md) | Medium | Sorted two pointers |
| 23 | [3Sum](./md_files/array/ThreeSum.md) | Medium | Sorting + pointers |
| 24 | [3Sum with Target (Indices, Hash Map)](./md_files/array/ThreeSumIndices.md) | Medium | Fix one + Two Sum hash map |
| 25 | [4Sum](./md_files/array/FourSum.md) | Medium | Multiple pointers |
| 26 | [Container With Most Water](./md_files/array/ContainerWithMostWater.md) | Medium | Greedy pointers |
| 27 | [Trapping Rain Water](./md_files/array/TrappingRainWater.md) | Hard | Two pointers |
| 28 | [Longest Substring Without Repeating Characters](./md_files/string/LongestSubString.md) | Medium | Variable window |
| 29 | [Longest Repeating Character Replacement](./md_files/string/LongestRepeatingCharacterReplacement.md) | Medium | Window + frequency |
| 30 | [Permutation in String](./md_files/string/PermutationInString.md) | Medium | Fixed window |
| 31 | [Find All Anagrams in a String](./md_files/string/FindAllAnagramsInAString.md) | Medium | Sliding window |
| 32 | [Minimum Window Substring](./md_files/string/MinimumWindowSubstring.md) | Hard | Window + HashMap |
| 33 | [Sliding Window Maximum](./md_files/array/SlidingWindowMaximum.md) | Hard | Monotonic deque |
| 34 | [Reverse a String](./md_files/string/ReverseString.md) | Easy | Two pointers |
| 35 | [Palindrome Check (String and Number)](./md_files/string/PalindromeCheck.md) | Easy | Two pointers |
| 36 | [Valid Palindrome](./md_files/string/ValidPalindrome.md) | Easy | Two pointers |
| 37 | [Valid Palindrome II](./md_files/string/ValidPalindromeII.md) | Easy | Conditional deletion |
| 38 | [Longest Common Prefix](./md_files/string/LongestCommonPrefix.md) | Easy | String traversal |
| 39 | [Longest Palindromic Substring](./md_files/string/LongestPalindrome.md) | Medium | Expand around center |
| 40 | [Palindromic Substrings](./md_files/string/PalindromicSubstrings.md) | Medium | Expand around center |
| 41 | [String to Integer (atoi)](./md_files/string/StringToIntegerAtoi.md) | Medium | Parsing |
| 42 | [Roman to Integer](./md_files/string/RomanToInteger.md) | Easy | Mapping |
| 43 | [Integer to Roman](./md_files/string/IntegerToRoman.md) | Medium | Greedy |
| 44 | [Multiply Strings](./md_files/string/MultiplyStrings.md) | Medium | Manual arithmetic |
| 45 | [Encode and Decode Strings](./md_files/string/EncodeAndDecodeStrings.md) | Medium | Serialization |
| 46 | [String Compression](./md_files/string/StringCompression.md) | Medium | Two pointers |

### 02. Hashing

*HashMap, HashSet, frequency maps*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 47 | [Two Sum](./md_files/array/TwoSum.md) | Easy | HashMap |
| 48 | [Contains Duplicate](./md_files/array/ContainsDuplicate.md) | Easy | HashSet |
| 49 | [Intersection of Two Arrays](./md_files/array/IntersectionOfTwoArrays.md) | Easy | HashSet |
| 50 | [Valid Anagram](./md_files/string/Anagram.md) | Easy | Character frequency |
| 51 | [Group Anagrams](./md_files/string/GroupAnagrams.md) | Medium | Frequency / Sorting |
| 52 | [Isomorphic Strings](./md_files/string/IsomorphicStrings.md) | Easy | Two-way mapping |
| 53 | [First Non-Repeating Character](./md_files/string/FirstNonRepeatingCharacter.md) | Easy | Frequency array |
| 54 | [Longest Consecutive Sequence](./md_files/array/LongestConsecutiveSequence.md) | Medium | HashSet |
| 55 | [Design HashMap](./md_files/design/CustomHashMap.md) | Medium | Hashing + collision handling |
| 56 | [Design HashSet](./md_files/design/DesignHashSet.md) | Easy | Hashing |

### 03. Linked Lists

*Reversal, cycles, merge, pointers*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 57 | [Single Linked List](./md_files/linkedlist/SingleLinkedListDemo.md) | Easy | Linked list implementation |
| 58 | [Reverse Linked List](./md_files/linkedlist/ReverseLinkedList.md) | Easy | Iterative reversal |
| 59 | [Reverse Linked List II](./md_files/linkedlist/ReverseLinkedListII.md) | Medium | Partial reversal |
| 60 | [Middle of the Linked List](./md_files/linkedlist/MiddleOfTheLinkedList.md) | Easy | Fast and slow pointers |
| 61 | [Merge Two Sorted Lists](./md_files/linkedlist/MergeTwoSortedLists.md) | Easy | Dummy node |
| 62 | [Linked List Cycle](./md_files/linkedlist/LinkedListCycle.md) | Easy | Floyd's algorithm |
| 63 | [Linked List Cycle II](./md_files/linkedlist/LinkedListCycleII.md) | Medium | Cycle entry detection |
| 64 | [Find the Duplicate Number](./md_files/linkedlist/FindTheDuplicateNumber.md) | Medium | Floyd's cycle detection |
| 65 | [Add Two Numbers](./md_files/linkedlist/AddTwoNumbers.md) | Medium | Carry handling |
| 66 | [Remove Nth Node From End](./md_files/linkedlist/RemoveNthNodeFromEnd.md) | Medium | Two pointers |
| 67 | [Palindrome Linked List](./md_files/linkedlist/PalindromeLinkedList.md) | Easy | Reverse second half |
| 68 | [Intersection of Two Linked Lists](./md_files/linkedlist/IntersectionOfTwoLinkedLists.md) | Easy | Pointer switching |
| 69 | [Reorder List](./md_files/linkedlist/ReorderList.md) | Medium | Split + reverse + merge |
| 70 | [Copy List with Random Pointer](./md_files/linkedlist/CopyListWithRandomPointer.md) | Medium | HashMap / interweaving |
| 71 | [Sort List](./md_files/linkedlist/SortList.md) | Medium | Merge sort |
| 72 | [Merge K Sorted Lists](./md_files/linkedlist/MergeKSortedLists.md) | Hard | PriorityQueue |
| 73 | [Reverse Nodes in K-Group](./md_files/linkedlist/ReverseNodesInKGroup.md) | Hard | Group reversal |

### 04. Stacks & Queues

*Monotonic stack, deque, expressions*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 74 | [Valid Parentheses](./md_files/stack/ValidParentheses.md) | Easy | Stack |
| 75 | [Min Stack](./md_files/stack/MinStack.md) | Medium | Auxiliary stack |
| 76 | [Implement Queue Using Stacks](./md_files/stack/ImplementQueueUsingStacks.md) | Easy | Two stacks |
| 77 | [Implement Stack Using Queues](./md_files/stack/ImplementStackUsingQueues.md) | Easy | Queue |
| 78 | [Evaluate Reverse Polish Notation](./md_files/stack/EvaluateReversePolishNotation.md) | Medium | Stack |
| 79 | [Decode String](./md_files/stack/DecodeString.md) | Medium | Stack |
| 80 | [Minimum Remove to Make Valid Parentheses](./md_files/stack/MinimumRemoveToMakeValidParentheses.md) | Medium | Stack |
| 81 | [Longest Valid Parentheses](./md_files/stack/LongestValidParentheses.md) | Hard | Stack / DP |
| 82 | [Daily Temperatures](./md_files/stack/DailyTemperatures.md) | Medium | Monotonic stack |
| 83 | [Next Greater Element I](./md_files/stack/NextGreaterElementI.md) | Easy | Monotonic stack |
| 84 | [Next Greater Element II](./md_files/stack/NextGreaterElementII.md) | Medium | Circular array |
| 85 | [Online Stock Span](./md_files/stack/OnlineStockSpan.md) | Medium | Monotonic stack |
| 86 | [Asteroid Collision](./md_files/stack/AsteroidCollision.md) | Medium | Stack simulation |
| 87 | [Car Fleet](./md_files/stack/CarFleet.md) | Medium | Sorting + stack |
| 88 | [Largest Rectangle in Histogram](./md_files/stack/LargestRectangleInHistogram.md) | Hard | Monotonic stack |
| 89 | [Basic Calculator](./md_files/stack/BasicCalculator.md) | Hard | Stack / Parsing |

### 05. Binary Search

*Search spaces, rotated arrays*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 90 | [Linear Search](./md_files/search/LinearSearch.md) | Easy | Sequential scan |
| 91 | [Binary Search](./md_files/search/BinarySearch.md) | Easy | Basic binary search |
| 92 | [Search Insert Position](./md_files/search/SearchInsertPosition.md) | Easy | Lower bound |
| 93 | [First and Last Position of Element](./md_files/search/FirstAndLastPosition.md) | Medium | Boundary search |
| 94 | [Search in Rotated Sorted Array](./md_files/search/SearchInRotatedSortedArray.md) | Medium | Modified binary search |
| 95 | [Find Minimum in Rotated Sorted Array](./md_files/search/FindMinimumInRotatedSortedArray.md) | Medium | Binary search |
| 96 | [Find Peak Element](./md_files/search/FindPeakElement.md) | Medium | Binary search |
| 97 | [Search a 2D Matrix](./md_files/search/SearchA2DMatrix.md) | Medium | Matrix binary search |
| 98 | [Koko Eating Bananas](./md_files/search/KokoEatingBananas.md) | Medium | Binary search on answer |
| 99 | [Capacity to Ship Packages Within D Days](./md_files/search/CapacityToShipPackages.md) | Medium | Feasibility search |
| 100 | [Median of Two Sorted Arrays](./md_files/search/MedianOfTwoSortedArrays.md) | Hard | Partition-based search |

### 06. Sorting & Intervals

*Merge intervals, custom sorting*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 101 | [Bubble Sort](./md_files/sort/BubbleSort.md) | Easy | Adjacent swaps |
| 102 | [Selection Sort](./md_files/sort/SelectionSort.md) | Easy | Minimum selection |
| 103 | [Insertion Sort](./md_files/sort/InsertionSort.md) | Easy | Shifting |
| 104 | [Merge Sort](./md_files/sort/MergeSort.md) | Medium | Divide and conquer |
| 105 | [Quick Sort](./md_files/sort/QuickSort.md) | Medium | Partitioning |
| 106 | [Heap Sort](./md_files/sort/HeapSort.md) | Medium | Binary heap |
| 107 | [Sort an Array](./md_files/sort/SortAnArray.md) | Medium | Merge / Quick sort |
| 108 | [Merge Intervals](./md_files/interval/MergeIntervals.md) | Medium | Sorting |
| 109 | [Insert Interval](./md_files/interval/InsertInterval.md) | Medium | Interval merging |
| 110 | [Non-overlapping Intervals](./md_files/interval/NonOverlappingIntervals.md) | Medium | Greedy |
| 111 | [Meeting Rooms](./md_files/interval/MeetingRooms.md) | Easy | Sorting |
| 112 | [Meeting Rooms II](./md_files/interval/MeetingRoomsII.md) | Medium | Min heap |
| 113 | [Minimum Interval to Include Each Query](./md_files/interval/MinimumIntervalToIncludeEachQuery.md) | Hard | Sorting + min heap |

### 07. Trees & BST

*DFS, BFS, traversal, construction*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 114 | [Binary Tree Traversals (Inorder, Preorder, Postorder)](./md_files/tree/TreeTraversals.md) | Easy | DFS orders |
| 115 | [Maximum Depth of Binary Tree](./md_files/tree/MaximumDepthOfBinaryTree.md) | Easy | DFS |
| 116 | [Minimum Depth of Binary Tree](./md_files/tree/MinimumDepthOfBinaryTree.md) | Easy | BFS |
| 117 | [Invert Binary Tree](./md_files/tree/InvertBinaryTree.md) | Easy | Recursion |
| 118 | [Same Tree](./md_files/tree/SameTree.md) | Easy | Tree comparison |
| 119 | [Symmetric Tree](./md_files/tree/SymmetricTree.md) | Easy | Recursive comparison |
| 120 | [Subtree of Another Tree](./md_files/tree/SubtreeOfAnotherTree.md) | Easy | Recursion |
| 121 | [Binary Tree Level Order Traversal](./md_files/tree/BinaryTreeLevelOrderTraversal.md) | Medium | BFS |
| 122 | [Binary Tree Zigzag Level Order Traversal](./md_files/tree/BinaryTreeZigzagLevelOrder.md) | Medium | BFS |
| 123 | [Binary Tree Right Side View](./md_files/tree/BinaryTreeRightSideView.md) | Medium | BFS / DFS |
| 124 | [Vertical Order Traversal](./md_files/tree/VerticalOrderTraversal.md) | Hard | BFS + Sorting |
| 125 | [Diameter of Binary Tree](./md_files/tree/DiameterOfBinaryTree.md) | Easy | DFS |
| 126 | [Balanced Binary Tree](./md_files/tree/BalancedBinaryTree.md) | Easy | Height calculation |
| 127 | [Count Good Nodes in Binary Tree](./md_files/tree/CountGoodNodesInBinaryTree.md) | Medium | DFS with running maximum |
| 128 | [Lowest Common Ancestor](./md_files/tree/LowestCommonAncestorOfBinaryTree.md) | Medium | Tree recursion |
| 129 | [Binary Search Tree](./md_files/tree/BinarySearchTree.md) | Medium | BST implementation |
| 130 | [Validate Binary Search Tree](./md_files/tree/ValidateBinarySearchTree.md) | Medium | Range validation |
| 131 | [Kth Smallest Element in BST](./md_files/tree/KthSmallestElementInBST.md) | Medium | Inorder traversal |
| 132 | [Lowest Common Ancestor of a Binary Search Tree](./md_files/tree/LowestCommonAncestorOfBST.md) | Medium | BST property |
| 133 | [Construct Tree from Preorder and Inorder](./md_files/tree/ConstructBinaryTreeFromPreorderInorder.md) | Medium | Recursion + HashMap |
| 134 | [Path Sum II](./md_files/tree/PathSumII.md) | Medium | Backtracking |
| 135 | [Flatten Binary Tree to Linked List](./md_files/tree/FlattenBinaryTreeToLinkedList.md) | Medium | Tree transformation |
| 136 | [Serialize and Deserialize Binary Tree](./md_files/tree/SerializeDeserializeBinaryTree.md) | Hard | BFS / DFS |
| 137 | [Binary Tree Maximum Path Sum](./md_files/tree/BinaryTreeMaximumPathSum.md) | Hard | DFS |
| 138 | [Binary Tree Cameras](./md_files/tree/BinaryTreeCameras.md) | Hard | Greedy / DFS |

### 08. Heaps

*PriorityQueue, Top K, K-way merge*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 139 | [Kth Largest Element in a Stream](./md_files/heap/KthLargestElementInAStream.md) | Easy | Min heap of size k |
| 140 | [Last Stone Weight](./md_files/heap/LastStoneWeight.md) | Easy | Max heap |
| 141 | [Kth Largest Element in an Array](./md_files/heap/KthLargestElementInAnArray.md) | Medium | Quickselect / Heap |
| 142 | [Top K Frequent Elements](./md_files/heap/TopKFrequentElements.md) | Medium | HashMap + Heap |
| 143 | [K Closest Points to Origin](./md_files/heap/KClosestPointsToOrigin.md) | Medium | PriorityQueue |
| 144 | [Find Median from Data Stream](./md_files/heap/FindMedianFromDataStream.md) | Hard | Two heaps |
| 145 | [Task Scheduler](./md_files/heap/TaskScheduler.md) | Medium | Heap + Greedy |
| 146 | [Reorganize String](./md_files/heap/ReorganizeString.md) | Medium | Max heap |
| 147 | [Find K Pairs with Smallest Sums](./md_files/heap/FindKPairsWithSmallestSums.md) | Medium | Heap |
| 148 | [Smallest Range Covering Elements from K Lists](./md_files/heap/SmallestRangeCoveringKLists.md) | Hard | Heap |

### 09. Graphs

*BFS, DFS, Dijkstra, Union-Find*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 149 | [Graph Traversals (BFS and DFS)](./md_files/graph/GraphTraversals.md) | Easy | BFS and DFS |
| 150 | [Flood Fill](./md_files/graph/FloodFill.md) | Easy | DFS |
| 151 | [Number of Islands](./md_files/graph/NumberOfIslands.md) | Medium | DFS / BFS |
| 152 | [Max Area of Island](./md_files/graph/MaxAreaOfIsland.md) | Medium | DFS |
| 153 | [Surrounded Regions](./md_files/graph/SurroundedRegions.md) | Medium | Border DFS |
| 154 | [Pacific Atlantic Water Flow](./md_files/graph/PacificAtlanticWaterFlow.md) | Medium | Multi-source DFS |
| 155 | [Rotting Oranges](./md_files/graph/RottingOranges.md) | Medium | Multi-source BFS |
| 156 | [Walls and Gates](./md_files/graph/WallsAndGates.md) | Medium | Multi-source BFS |
| 157 | [Clone Graph](./md_files/graph/CloneGraph.md) | Medium | DFS + HashMap |
| 158 | [Course Schedule](./md_files/graph/CourseSchedule.md) | Medium | Cycle detection |
| 159 | [Word Ladder](./md_files/graph/WordLadder.md) | Hard | BFS |
| 160 | [Evaluate Division](./md_files/graph/EvaluateDivision.md) | Medium | Weighted graph |
| 161 | [Graph Valid Tree](./md_files/graph/GraphValidTree.md) | Medium | DFS / Union-Find |
| 162 | [Number of Connected Components](./md_files/graph/NumberOfConnectedComponents.md) | Medium | Union-Find |
| 163 | [Redundant Connection](./md_files/graph/RedundantConnection.md) | Medium | DSU |
| 164 | [Accounts Merge](./md_files/graph/AccountsMerge.md) | Medium | Union-Find |
| 165 | [Implement Union-Find from Scratch](./md_files/graph/UnionFind.md) | Medium | Path compression + Union by Rank |
| 166 | [Network Delay Time](./md_files/graph/NetworkDelayTime.md) | Medium | Dijkstra |
| 167 | [Cheapest Flights Within K Stops](./md_files/graph/CheapestFlightsWithinKStops.md) | Medium | Bellman-Ford / BFS |
| 168 | [Implement Dijkstra from Scratch](./md_files/graph/DijkstraShortestPath.md) | Medium | Graph + PriorityQueue |
| 169 | [Swim in Rising Water](./md_files/graph/SwimInRisingWater.md) | Hard | Dijkstra / binary search |

### 10. Dynamic Programming

*Memoization, tabulation, optimization*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 170 | [Climbing Stairs](./md_files/dp/ClimbingStairs.md) | Easy | 1D DP |
| 171 | [Min Cost Climbing Stairs](./md_files/dp/MinCostClimbingStairs.md) | Easy | 1D DP |
| 172 | [House Robber](./md_files/dp/HouseRobber.md) | Medium | DP |
| 173 | [House Robber II](./md_files/dp/HouseRobberII.md) | Medium | Circular DP |
| 174 | [Decode Ways](./md_files/dp/DecodeWays.md) | Medium | 1D DP |
| 175 | [Coin Change](./md_files/dp/CoinChange.md) | Medium | Unbounded knapsack |
| 176 | [Word Break](./md_files/dp/WordBreak.md) | Medium | DP + HashSet |
| 177 | [Longest Increasing Subsequence](./md_files/dp/LongestIncreasingSubsequence.md) | Medium | DP / Binary search |
| 178 | [Unique Paths](./md_files/dp/UniquePaths.md) | Medium | Grid DP |
| 179 | [Minimum Path Sum](./md_files/dp/MinimumPathSum.md) | Medium | Grid DP |
| 180 | [Best Time to Buy and Sell Stock with Cooldown](./md_files/dp/BestTimeToBuyAndSellStockWithCooldown.md) | Medium | State machine DP |
| 181 | [Longest Common Subsequence](./md_files/dp/LongestCommonSubsequence.md) | Medium | 2D DP |
| 182 | [Longest Palindromic Subsequence](./md_files/dp/LongestPalindromicSubsequence.md) | Medium | 2D DP |
| 183 | [Edit Distance](./md_files/dp/EditDistance.md) | Medium | 2D DP |
| 184 | [Interleaving String](./md_files/dp/InterleavingString.md) | Medium | 2D DP |

### 11. Backtracking

*Permutations, combinations, subsets*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 185 | [Subsets](./md_files/backtracking/Subsets.md) | Medium | Decision tree |
| 186 | [Subsets II](./md_files/backtracking/SubsetsII.md) | Medium | Duplicate handling |
| 187 | [Permutations](./md_files/backtracking/Permutations.md) | Medium | Backtracking |
| 188 | [Permutations II](./md_files/backtracking/PermutationsII.md) | Medium | Duplicate handling |
| 189 | [Combination Sum](./md_files/backtracking/CombinationSum.md) | Medium | Reusable choices |
| 190 | [Combination Sum II](./md_files/backtracking/CombinationSumII.md) | Medium | Single-use choices |
| 191 | [Letter Combinations of a Phone Number](./md_files/backtracking/LetterCombinationsOfAPhoneNumber.md) | Medium | Backtracking |
| 192 | [Generate Parentheses](./md_files/backtracking/GenerateParentheses.md) | Medium | Recursive construction |
| 193 | [Palindrome Partitioning](./md_files/backtracking/PalindromePartitioning.md) | Medium | Backtracking + palindrome check |
| 194 | [Word Search](./md_files/backtracking/WordSearch.md) | Medium | DFS + Backtracking |
| 195 | [N-Queens](./md_files/backtracking/NQueens.md) | Hard | Constraint search |
| 196 | [Sudoku Solver](./md_files/backtracking/SudokuSolver.md) | Hard | Constraint propagation |

### 12. Greedy Algorithms

*Scheduling, selection, optimization*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 197 | [Jump Game](./md_files/greedy/JumpGame.md) | Medium | Greedy |
| 198 | [Jump Game II](./md_files/greedy/JumpGameII.md) | Medium | Greedy |
| 199 | [Gas Station](./md_files/greedy/GasStation.md) | Medium | Greedy |
| 200 | [Candy](./md_files/greedy/Candy.md) | Hard | Two-pass greedy |
| 201 | [Hand of Straights](./md_files/greedy/HandOfStraights.md) | Medium | Greedy + TreeMap |
| 202 | [Merge Triplets to Form Target Triplet](./md_files/greedy/MergeTripletsToFormTargetTriplet.md) | Medium | Greedy |
| 203 | [Partition Labels](./md_files/greedy/PartitionLabels.md) | Medium | Last occurrence |
| 204 | [Valid Parenthesis String](./md_files/greedy/ValidParenthesisString.md) | Medium | Greedy range |

### 13. Tries

*Prefix search, autocomplete*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 205 | [Implement Trie](./md_files/trie/ImplementTrie.md) | Medium | Prefix tree |
| 206 | [Design Add and Search Words](./md_files/trie/DesignAddAndSearchWords.md) | Medium | Trie + DFS |
| 207 | [Word Search II](./md_files/trie/WordSearchII.md) | Hard | Trie + Backtracking |

### 14. Bit Manipulation

*XOR, masks, bit operations*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 208 | [Single Number](./md_files/bit/SingleNumber.md) | Easy | XOR |
| 209 | [Single Number II](./md_files/bit/SingleNumberII.md) | Medium | Bit manipulation |
| 210 | [Counting Bits](./md_files/bit/CountingBits.md) | Easy | DP + Bits |
| 211 | [Reverse Bits](./md_files/bit/ReverseBits.md) | Easy | Bit operations |
| 212 | [Number of 1 Bits](./md_files/bit/NumberOf1Bits.md) | Easy | Brian Kernighan |

### 15. Recursion

*Recurrence, recursion trees*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 213 | [Factorial](./md_files/math/Factorial.md) | Easy | Recursion / iteration |
| 214 | [Fibonacci](./md_files/math/Fibonacci.md) | Easy | Recursion / memoization |

### 16. Advanced Graphs

*Topological sort, MST, SCC*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 215 | [Course Schedule II](./md_files/graph/CourseScheduleII.md) | Medium | Topological sort |
| 216 | [Topological Sort](./md_files/graph/TopologicalSort.md) | Medium | Kahn's algorithm / DFS |
| 217 | [Alien Dictionary](./md_files/graph/AlienDictionary.md) | Hard | Topological sort |
| 218 | [Minimum Spanning Tree](./md_files/graph/MinimumSpanningTree.md) | Medium | Kruskal / Prim |
| 219 | [Min Cost to Connect All Points](./md_files/graph/MinCostToConnectAllPoints.md) | Medium | Prim / Kruskal |
| 220 | [Critical Connections in a Network](./md_files/graph/CriticalConnections.md) | Hard | Tarjan's algorithm |
| 221 | [Reconstruct Itinerary](./md_files/graph/ReconstructItinerary.md) | Hard | Eulerian path (Hierholzer) |

### 17. Advanced DP

*Knapsack, interval DP, tree DP*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 222 | [Coin Change II](./md_files/dp/CoinChangeII.md) | Medium | Combination DP |
| 223 | [Partition Equal Subset Sum](./md_files/dp/PartitionEqualSubsetSum.md) | Medium | Knapsack |
| 224 | [Target Sum](./md_files/dp/TargetSum.md) | Medium | Knapsack transformation |
| 225 | [0/1 Knapsack](./md_files/dp/Knapsack01.md) | Medium | 0/1 knapsack |
| 226 | [Distinct Subsequences](./md_files/dp/DistinctSubsequences.md) | Hard | 2D DP |
| 227 | [Regular Expression Matching](./md_files/dp/RegularExpressionMatching.md) | Hard | 2D DP |
| 228 | [Burst Balloons](./md_files/dp/BurstBalloons.md) | Hard | Interval DP |
| 229 | [Longest Increasing Path in a Matrix](./md_files/dp/LongestIncreasingPathInAMatrix.md) | Hard | DFS + Memoization |
| 230 | [Maximum Profit in Job Scheduling](./md_files/dp/MaximumProfitInJobScheduling.md) | Hard | DP + Binary search |

### 18. Math & Geometry

*GCD, primes, coordinates*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 231 | [Prime Numbers](./md_files/math/PrimeNumbers.md) | Easy | Sieve of Eratosthenes |
| 232 | [GCD and LCM](./md_files/math/GcdAndLcm.md) | Easy | Euclid's algorithm |
| 233 | [Armstrong Number](./md_files/math/ArmstrongNumber.md) | Easy | Digit math |
| 234 | [FizzBuzz](./md_files/math/FizzBuzz.md) | Easy | Modulo |
| 235 | [Swap Two Numbers Without a Temporary Variable](./md_files/math/SwapTwoNumbers.md) | Easy | XOR / arithmetic |

### 19. Design Problems

*LRU, LFU, data structures*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 236 | [LRU Cache](./md_files/design/LRUCache.md) | Medium | HashMap + Doubly Linked List |
| 237 | [LFU Cache](./md_files/design/LFUCache.md) | Hard | HashMap + Frequency Lists |
| 238 | [Design Hit Counter](./md_files/design/DesignHitCounter.md) | Medium | Queue + Sliding window |
| 239 | [Logger Rate Limiter](./md_files/design/LoggerRateLimiter.md) | Easy | HashMap + Time |
| 240 | [Time Based Key-Value Store](./md_files/design/TimeBasedKeyValueStore.md) | Medium | HashMap + Binary search |
| 241 | [Design Twitter](./md_files/design/DesignTwitter.md) | Medium | Heap + HashMap |
| 242 | [Insert Delete GetRandom O(1)](./md_files/design/InsertDeleteGetRandom.md) | Medium | ArrayList + HashMap |
| 243 | [All O(1) Data Structure](./md_files/design/AllOneDataStructure.md) | Hard | Doubly linked list + HashMap |
| 244 | [Design Circular Queue](./md_files/design/DesignCircularQueue.md) | Medium | Array / Circular buffer |
| 245 | [Design Skip List](./md_files/design/DesignSkipList.md) | Hard | Probabilistic data structure |
| 246 | [Design a Min/Max Heap](./md_files/design/BinaryHeap.md) | Medium | Heap implementation |

### 20. Advanced Algorithms

*KMP, Rabin-Karp, segment trees*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 247 | [Range Sum Query - Mutable](./md_files/design/RangeSumQueryMutable.md) | Medium | Segment tree / Fenwick tree |

### 21. Java Core & Concurrency

*Threads, locks, immutability, streams*

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 248 | [Producer-Consumer Implementation](./md_files/threads/ProducerConsumer.md) | Medium | Raw monitor synchronization (wait() / notifyAll()) |
| 249 | [Print Odd and Even Alternately](./md_files/threads/PrintEvenOddUsingThreads.md) | Medium | Thread coordination using wait/notify or semaphores |
| 250 | [Print Numbers in Sequence with Three Threads](./md_files/threads/PrintNumbersWithThreeThreads.md) | Medium | wait / notifyAll |
| 251 | [Concurrency Coordination Puzzles](./md_files/threads/ConcurrencyCoordinationPuzzles.md) | Medium | Print in Order / FooBar sequence synchronization |
| 252 | [Thread-Safe BlockingQueue](./md_files/threads/BoundedBlockingQueue.md) | Hard | ReentrantLock + Conditions (notFull, notEmpty) |
| 253 | [Custom Simple Thread Pool](./md_files/threads/SimpleThreadPool.md) | Hard | Worker thread pool with a task queue |
| 254 | [Custom Read-Write Lock](./md_files/threads/CustomReadWriteLock.md) | Hard | State-based tracking for multiple readers / single writer |
| 255 | [Deadlock and How to Avoid It](./md_files/threads/DeadlockDemo.md) | Medium | Lock ordering |
| 256 | [Thread-Safe Singleton Variations](./md_files/core/SingletonPattern.md) | Medium | Double-Checked Locking (volatile) and Bill Pugh Holder pattern |
| 257 | [Fully Immutable Class with Mutable Fields](./md_files/core/ImmutableClassDemo.md) | Medium | Deep defensive copying for constructors and getters |
| 258 | [Custom Functional Interface and Collector](./md_files/core/CustomCollectors.md) | Hard | Custom stream reduction aggregation logic |
| 259 | [Custom Resource Manager (AutoCloseable)](./md_files/core/ResourceManager.md) | Medium | Try-with-resources integration with exception suppression |
| 260 | [Java 8 Streams Interview Questions](./md_files/core/StreamsInterviewQuestions.md) | Medium | Java 8 streams |
