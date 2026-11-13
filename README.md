# Java Interview Programs

!["Java Interview Programs"](./images/Java_Interview_Programs.png)

## Coding Interview Questions by Topic

Interview problems grouped by topic, in a recommended study order. Each page explains the strategy, shows several approaches from brute force to optimized, states the time and space complexity, and includes the Java source.

A few problems are listed under two topics; both entries link to the same page.

| # | Topic | Questions |
|---|-------|-----------|
| 1 | [Arrays and Hashing](#1-arrays-and-hashing) | 20 |
| 2 | [Two Pointers and Sliding Window](#2-two-pointers-and-sliding-window) | 15 |
| 3 | [Strings](#3-strings) | 12 |
| 4 | [Linked Lists](#4-linked-lists) | 15 |
| 5 | [Stack and Queue](#5-stack-and-queue) | 12 |
| 6 | [Binary Search and Sorting](#6-binary-search-and-sorting) | 12 |
| 7 | [Trees and Binary Search Trees](#7-trees-and-binary-search-trees) | 20 |
| 8 | [Heap and Priority Queue](#8-heap-and-priority-queue) | 10 |
| 9 | [Graph Algorithms](#9-graph-algorithms) | 18 |
| 10 | [Dynamic Programming](#10-dynamic-programming) | 20 |
| 11 | [Backtracking and Recursion](#11-backtracking-and-recursion) | 10 |
| 12 | [Greedy Algorithms and Intervals](#12-greedy-algorithms-and-intervals) | 10 |
| 13 | [Tries, Bit Manipulation and Advanced Data Structures](#13-tries-bit-manipulation-and-advanced-data-structures) | 12 |
| 14 | [Advanced Coding and Implementation Problems](#14-advanced-coding-and-implementation-problems) | 14 |
| 15 | [Java Concurrency, Multithreading and Core Design](#15-java-concurrency-multithreading-and-core-design) | 10 |

### 1. Arrays and Hashing

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 1 | [Two Sum](./md_files/array/TwoSum.md) | Easy | HashMap |
| 2 | [Contains Duplicate](./md_files/array/ContainsDuplicate.md) | Easy | HashSet |
| 3 | [Best Time to Buy and Sell Stock](./md_files/array/BestTimeToBuyAndSellStock.md) | Easy | Greedy |
| 4 | [Maximum Subarray](./md_files/array/MaximumSubarray.md) | Medium | Kadane's algorithm |
| 5 | [Product of Array Except Self](./md_files/array/ProductOfArrayExceptSelf.md) | Medium | Prefix/suffix |
| 6 | [Maximum Product Subarray](./md_files/array/MaximumProductSubarray.md) | Medium | Dynamic tracking |
| 7 | [Majority Element](./md_files/array/MajorityElement.md) | Easy | Boyer-Moore |
| 8 | [Majority Element II](./md_files/array/MajorityElementII.md) | Medium | Extended Boyer-Moore |
| 9 | [Missing Number](./md_files/array/MissingNumber.md) | Easy | XOR / Math |
| 10 | [Find All Numbers Disappeared in an Array](./md_files/array/FindAllNumbersDisappeared.md) | Easy | In-place marking |
| 11 | [First Missing Positive](./md_files/array/FirstMissingPositive.md) | Hard | Index placement |
| 12 | [Subarray Sum Equals K](./md_files/array/SubarraySumEqualsK.md) | Medium | Prefix sum + HashMap |
| 13 | [Longest Consecutive Sequence](./md_files/graph/LongestConsecutiveSequence.md) | Medium | HashSet |
| 14 | [Group Anagrams](./md_files/string/GroupAnagrams.md) | Medium | Frequency / Sorting |
| 15 | [Top K Frequent Elements](./md_files/heap/TopKFrequentElements.md) | Medium | HashMap + Heap |
| 16 | [Sort Colors](./md_files/array/MoveZerosAndOnes.md) | Medium | Dutch National Flag |
| 17 | [Set Matrix Zeroes](./md_files/matrix/SetMatrixZeroes.md) | Medium | In-place matrix |
| 18 | [Rotate Image](./md_files/matrix/RotateImage.md) | Medium | Matrix transpose |
| 19 | [Spiral Matrix](./md_files/matrix/SpiralMatrix.md) | Medium | Boundary traversal |
| 20 | [Merge Sorted Array](./md_files/array/MergeSortedArray.md) | Easy | Two pointers |

### 2. Two Pointers and Sliding Window

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 21 | [Valid Palindrome](./md_files/string/ValidPalindrome.md) | Easy | Two pointers |
| 22 | [Valid Palindrome II](./md_files/string/ValidPalindromeII.md) | Easy | Conditional deletion |
| 23 | [Two Sum II](./md_files/array/TwoSumII.md) | Medium | Sorted two pointers |
| 24 | [3Sum](./md_files/array/ThreeSum.md) | Medium | Sorting + pointers |
| 25 | [4Sum](./md_files/array/FourSum.md) | Medium | Multiple pointers |
| 26 | [Container With Most Water](./md_files/array/ContainerWithMostWater.md) | Medium | Greedy pointers |
| 27 | [Trapping Rain Water](./md_files/array/TrappingRainWater.md) | Hard | Two pointers |
| 28 | [Remove Duplicates from Sorted Array](./md_files/array/RemoveDuplicates.md) | Easy | In-place pointers |
| 29 | [Move Zeroes](./md_files/array/MoveZeroes.md) | Easy | Fast and slow |
| 30 | [Longest Substring Without Repeating Characters](./md_files/string/LongestSubString.md) | Medium | Variable window |
| 31 | [Longest Repeating Character Replacement](./md_files/string/LongestRepeatingCharacterReplacement.md) | Medium | Window + frequency |
| 32 | [Permutation in String](./md_files/string/PermutationInString.md) | Medium | Fixed window |
| 33 | [Find All Anagrams in a String](./md_files/string/FindAllAnagramsInAString.md) | Medium | Sliding window |
| 34 | [Minimum Window Substring](./md_files/string/MinimumWindowSubstring.md) | Hard | Window + HashMap |
| 35 | [Sliding Window Maximum](./md_files/array/SlidingWindowMaximum.md) | Hard | Monotonic deque |

### 3. Strings

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 36 | [Valid Anagram](./md_files/basic/Anagram.md) | Easy | Character frequency |
| 37 | [Longest Common Prefix](./md_files/string/LongestCommonPrefix.md) | Easy | String traversal |
| 38 | [Longest Palindromic Substring](./md_files/string/LongestPalindrome.md) | Medium | Expand around center |
| 39 | [Palindromic Substrings](./md_files/string/PalindromicSubstrings.md) | Medium | Expand around center |
| 40 | [String to Integer (atoi)](./md_files/string/StringToIntegerAtoi.md) | Medium | Parsing |
| 41 | [Integer to Roman](./md_files/string/IntegerToRoman.md) | Medium | Greedy |
| 42 | [Roman to Integer](./md_files/string/RomanToInteger.md) | Easy | Mapping |
| 43 | [Encode and Decode Strings](./md_files/string/EncodeAndDecodeStrings.md) | Medium | Serialization |
| 44 | [Multiply Strings](./md_files/string/MultiplyStrings.md) | Medium | Manual arithmetic |
| 45 | [Decode String](./md_files/string/DecodeString.md) | Medium | Stack |
| 46 | [Longest Valid Parentheses](./md_files/string/LongestValidParentheses.md) | Hard | Stack / DP |
| 47 | [Minimum Remove to Make Valid Parentheses](./md_files/string/MinimumRemoveToMakeValidParentheses.md) | Medium | Stack |

### 4. Linked Lists

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 48 | [Reverse Linked List](./md_files/linkedlist/ReverseLinkedList.md) | Easy | Iterative reversal |
| 49 | [Reverse Linked List II](./md_files/linkedlist/ReverseLinkedListII.md) | Medium | Partial reversal |
| 50 | [Linked List Cycle](./md_files/linkedlist/LinkedListCycle.md) | Easy | Floyd's algorithm |
| 51 | [Linked List Cycle II](./md_files/linkedlist/LinkedListCycleII.md) | Medium | Cycle entry detection |
| 52 | [Middle of the Linked List](./md_files/linkedlist/MiddleOfTheLinkedList.md) | Easy | Fast and slow pointers |
| 53 | [Merge Two Sorted Lists](./md_files/linkedlist/MergeTwoSortedLists.md) | Easy | Dummy node |
| 54 | [Add Two Numbers](./md_files/linkedlist/AddTwoNumbers.md) | Medium | Carry handling |
| 55 | [Remove Nth Node From End](./md_files/linkedlist/RemoveNthNodeFromEnd.md) | Medium | Two pointers |
| 56 | [Reorder List](./md_files/linkedlist/ReorderList.md) | Medium | Split + reverse + merge |
| 57 | [Palindrome Linked List](./md_files/linkedlist/PalindromeLinkedList.md) | Easy | Reverse second half |
| 58 | [Intersection of Two Linked Lists](./md_files/linkedlist/IntersectionOfTwoLinkedLists.md) | Easy | Pointer switching |
| 59 | [Copy List with Random Pointer](./md_files/linkedlist/CopyListWithRandomPointer.md) | Medium | HashMap / interweaving |
| 60 | [Sort List](./md_files/linkedlist/SortList.md) | Medium | Merge sort |
| 61 | [Merge K Sorted Lists](./md_files/linkedlist/MergeKSortedLists.md) | Hard | PriorityQueue |
| 62 | [Reverse Nodes in K-Group](./md_files/linkedlist/ReverseNodesInKGroup.md) | Hard | Group reversal |

### 5. Stack and Queue

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 63 | [Valid Parentheses](./md_files/string/ValidParentheses.md) | Easy | Stack |
| 64 | [Min Stack](./md_files/stack/MinStack.md) | Medium | Auxiliary stack |
| 65 | [Implement Queue Using Stacks](./md_files/stack/ImplementQueueUsingStacks.md) | Easy | Two stacks |
| 66 | [Implement Stack Using Queues](./md_files/stack/ImplementStackUsingQueues.md) | Easy | Queue |
| 67 | [Evaluate Reverse Polish Notation](./md_files/stack/EvaluateReversePolishNotation.md) | Medium | Stack |
| 68 | [Daily Temperatures](./md_files/stack/DailyTemperatures.md) | Medium | Monotonic stack |
| 69 | [Next Greater Element I](./md_files/stack/NextGreaterElementI.md) | Easy | Monotonic stack |
| 70 | [Next Greater Element II](./md_files/stack/NextGreaterElementII.md) | Medium | Circular array |
| 71 | [Largest Rectangle in Histogram](./md_files/stack/LargestRectangleInHistogram.md) | Hard | Monotonic stack |
| 72 | [Asteroid Collision](./md_files/stack/AsteroidCollision.md) | Medium | Stack simulation |
| 73 | [Online Stock Span](./md_files/stack/OnlineStockSpan.md) | Medium | Monotonic stack |
| 74 | [Basic Calculator](./md_files/stack/BasicCalculator.md) | Hard | Stack / Parsing |

### 6. Binary Search and Sorting

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 75 | [Binary Search](./md_files/search/BinarySearch.md) | Easy | Basic binary search |
| 76 | [Search Insert Position](./md_files/search/SearchInsertPosition.md) | Easy | Lower bound |
| 77 | [First and Last Position of Element](./md_files/search/FirstAndLastPosition.md) | Medium | Boundary search |
| 78 | [Search in Rotated Sorted Array](./md_files/array/SearchInRotatedSortedArray.md) | Medium | Modified binary search |
| 79 | [Find Minimum in Rotated Sorted Array](./md_files/array/FindMinimumInRotatedSortedArray.md) | Medium | Binary search |
| 80 | [Find Peak Element](./md_files/array/FindPeakElement.md) | Medium | Binary search |
| 81 | [Search a 2D Matrix](./md_files/search/SearchA2DMatrix.md) | Medium | Matrix binary search |
| 82 | [Koko Eating Bananas](./md_files/search/KokoEatingBananas.md) | Medium | Binary search on answer |
| 83 | [Capacity to Ship Packages Within D Days](./md_files/search/CapacityToShipPackages.md) | Medium | Feasibility search |
| 84 | [Median of Two Sorted Arrays](./md_files/search/MedianOfTwoSortedArrays.md) | Hard | Partition-based search |
| 85 | [Sort an Array](./md_files/sort/SortAnArray.md) | Medium | Merge / Quick sort |
| 86 | [Kth Largest Element in an Array](./md_files/heap/KthLargestElementInAnArray.md) | Medium | Quickselect / Heap |

### 7. Trees and Binary Search Trees

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 87 | [Maximum Depth of Binary Tree](./md_files/tree/MaximumDepthOfBinaryTree.md) | Easy | DFS |
| 88 | [Minimum Depth of Binary Tree](./md_files/tree/MinimumDepthOfBinaryTree.md) | Easy | BFS |
| 89 | [Invert Binary Tree](./md_files/tree/InvertBinaryTree.md) | Easy | Recursion |
| 90 | [Same Tree](./md_files/tree/SameTree.md) | Easy | Tree comparison |
| 91 | [Symmetric Tree](./md_files/tree/SymmetricTree.md) | Easy | Recursive comparison |
| 92 | [Binary Tree Level Order Traversal](./md_files/tree/BinaryTreeLevelOrderTraversal.md) | Medium | BFS |
| 93 | [Binary Tree Zigzag Level Order Traversal](./md_files/tree/BinaryTreeZigzagLevelOrder.md) | Medium | BFS |
| 94 | [Binary Tree Right Side View](./md_files/tree/BinaryTreeRightSideView.md) | Medium | BFS / DFS |
| 95 | [Diameter of Binary Tree](./md_files/tree/DiameterOfBinaryTree.md) | Easy | DFS |
| 96 | [Balanced Binary Tree](./md_files/tree/BalancedBinaryTree.md) | Easy | Height calculation |
| 97 | [Lowest Common Ancestor](./md_files/tree/LowestCommonAncestorOfBinaryTree.md) | Medium | Tree recursion |
| 98 | [Validate Binary Search Tree](./md_files/tree/ValidateBinarySearchTree.md) | Medium | Range validation |
| 99 | [Kth Smallest Element in BST](./md_files/tree/KthSmallestElementInBST.md) | Medium | Inorder traversal |
| 100 | [Construct Tree from Preorder and Inorder](./md_files/tree/ConstructBinaryTreeFromPreorderInorder.md) | Medium | Recursion + HashMap |
| 101 | [Binary Tree Maximum Path Sum](./md_files/tree/BinaryTreeMaximumPathSum.md) | Hard | DFS |
| 102 | [Path Sum II](./md_files/tree/PathSumII.md) | Medium | Backtracking |
| 103 | [Flatten Binary Tree to Linked List](./md_files/tree/FlattenBinaryTreeToLinkedList.md) | Medium | Tree transformation |
| 104 | [Serialize and Deserialize Binary Tree](./md_files/tree/SerializeDeserializeBinaryTree.md) | Hard | BFS / DFS |
| 105 | [Vertical Order Traversal](./md_files/tree/VerticalOrderTraversal.md) | Hard | BFS + Sorting |
| 106 | [Binary Tree Cameras](./md_files/tree/BinaryTreeCameras.md) | Hard | Greedy / DFS |

### 8. Heap and Priority Queue

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 107 | [Kth Largest Element](./md_files/heap/KthLargestElementInAnArray.md) | Medium | Min heap |
| 108 | [Top K Frequent Elements](./md_files/heap/TopKFrequentElements.md) | Medium | Heap + HashMap |
| 109 | [K Closest Points to Origin](./md_files/heap/KClosestPointsToOrigin.md) | Medium | PriorityQueue |
| 110 | [Find Median from Data Stream](./md_files/heap/FindMedianFromDataStream.md) | Hard | Two heaps |
| 111 | [Merge K Sorted Lists](./md_files/linkedlist/MergeKSortedLists.md) | Hard | Min heap |
| 112 | [Task Scheduler](./md_files/heap/TaskScheduler.md) | Medium | Heap + Greedy |
| 113 | [Reorganize String](./md_files/heap/ReorganizeString.md) | Medium | Max heap |
| 114 | [Meeting Rooms II](./md_files/interval/MeetingRoomsII.md) | Medium | Min heap |
| 115 | [Smallest Range Covering Elements from K Lists](./md_files/heap/SmallestRangeCoveringKLists.md) | Hard | Heap |
| 116 | [Find K Pairs with Smallest Sums](./md_files/heap/FindKPairsWithSmallestSums.md) | Medium | Heap |

### 9. Graph Algorithms

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 117 | [Number of Islands](./md_files/graph/NumberOfIslands.md) | Medium | DFS / BFS |
| 118 | [Flood Fill](./md_files/graph/FloodFill.md) | Easy | DFS |
| 119 | [Clone Graph](./md_files/graph/CloneGraph.md) | Medium | DFS + HashMap |
| 120 | [Rotting Oranges](./md_files/graph/RottingOranges.md) | Medium | Multi-source BFS |
| 121 | [Course Schedule](./md_files/graph/CourseSchedule.md) | Medium | Cycle detection |
| 122 | [Course Schedule II](./md_files/graph/CourseScheduleII.md) | Medium | Topological sort |
| 123 | [Pacific Atlantic Water Flow](./md_files/graph/PacificAtlanticWaterFlow.md) | Medium | Multi-source DFS |
| 124 | [Word Ladder](./md_files/graph/WordLadder.md) | Hard | BFS |
| 125 | [Graph Valid Tree](./md_files/graph/GraphValidTree.md) | Medium | DFS / Union-Find |
| 126 | [Number of Connected Components](./md_files/graph/NumberOfConnectedComponents.md) | Medium | Union-Find |
| 127 | [Accounts Merge](./md_files/graph/AccountsMerge.md) | Medium | Union-Find |
| 128 | [Redundant Connection](./md_files/graph/RedundantConnection.md) | Medium | DSU |
| 129 | [Network Delay Time](./md_files/graph/NetworkDelayTime.md) | Medium | Dijkstra |
| 130 | [Cheapest Flights Within K Stops](./md_files/graph/CheapestFlightsWithinKStops.md) | Medium | Bellman-Ford / BFS |
| 131 | [Minimum Spanning Tree](./md_files/graph/MinimumSpanningTree.md) | Medium | Kruskal / Prim |
| 132 | [Alien Dictionary](./md_files/graph/AlienDictionary.md) | Hard | Topological sort |
| 133 | [Critical Connections in a Network](./md_files/graph/CriticalConnections.md) | Hard | Tarjan's algorithm |
| 134 | [Evaluate Division](./md_files/graph/EvaluateDivision.md) | Medium | Weighted graph |

### 10. Dynamic Programming

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 135 | [Climbing Stairs](./md_files/dp/ClimbingStairs.md) | Easy | 1D DP |
| 136 | [House Robber](./md_files/dp/HouseRobber.md) | Medium | DP |
| 137 | [House Robber II](./md_files/dp/HouseRobberII.md) | Medium | Circular DP |
| 138 | [Coin Change](./md_files/dp/CoinChange.md) | Medium | Unbounded knapsack |
| 139 | [Coin Change II](./md_files/dp/CoinChangeII.md) | Medium | Combination DP |
| 140 | [Longest Increasing Subsequence](./md_files/dp/LongestIncreasingSubsequence.md) | Medium | DP / Binary search |
| 141 | [Longest Common Subsequence](./md_files/dp/LongestCommonSubsequence.md) | Medium | 2D DP |
| 142 | [Word Break](./md_files/dp/WordBreak.md) | Medium | DP + HashSet |
| 143 | [Decode Ways](./md_files/dp/DecodeWays.md) | Medium | 1D DP |
| 144 | [Unique Paths](./md_files/dp/UniquePaths.md) | Medium | Grid DP |
| 145 | [Minimum Path Sum](./md_files/dp/MinimumPathSum.md) | Medium | Grid DP |
| 146 | [Partition Equal Subset Sum](./md_files/dp/PartitionEqualSubsetSum.md) | Medium | Knapsack |
| 147 | [Target Sum](./md_files/dp/TargetSum.md) | Medium | Knapsack transformation |
| 148 | [Edit Distance](./md_files/dp/EditDistance.md) | Medium | 2D DP |
| 149 | [Maximum Product Subarray](./md_files/array/MaximumProductSubarray.md) | Medium | State tracking |
| 150 | [Interleaving String](./md_files/dp/InterleavingString.md) | Medium | 2D DP |
| 151 | [Palindromic Substrings](./md_files/string/PalindromicSubstrings.md) | Medium | DP |
| 152 | [Regular Expression Matching](./md_files/dp/RegularExpressionMatching.md) | Hard | 2D DP |
| 153 | [Burst Balloons](./md_files/dp/BurstBalloons.md) | Hard | Interval DP |
| 154 | [Longest Increasing Path in a Matrix](./md_files/dp/LongestIncreasingPathInAMatrix.md) | Hard | DFS + Memoization |

### 11. Backtracking and Recursion

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 155 | [Subsets](./md_files/backtracking/Subsets.md) | Medium | Decision tree |
| 156 | [Subsets II](./md_files/backtracking/SubsetsII.md) | Medium | Duplicate handling |
| 157 | [Permutations](./md_files/backtracking/Permutations.md) | Medium | Backtracking |
| 158 | [Permutations II](./md_files/backtracking/PermutationsII.md) | Medium | Duplicate handling |
| 159 | [Combination Sum](./md_files/dp/CombinationSum.md) | Medium | Reusable choices |
| 160 | [Combination Sum II](./md_files/backtracking/CombinationSumII.md) | Medium | Single-use choices |
| 161 | [Word Search](./md_files/matrix/WordSearch.md) | Medium | DFS + Backtracking |
| 162 | [N-Queens](./md_files/backtracking/NQueens.md) | Hard | Constraint search |
| 163 | [Sudoku Solver](./md_files/backtracking/SudokuSolver.md) | Hard | Constraint propagation |
| 164 | [Generate Parentheses](./md_files/stack/GenerateParentheses.md) | Medium | Recursive construction |

### 12. Greedy Algorithms and Intervals

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 165 | [Merge Intervals](./md_files/array/MergeIntervals.md) | Medium | Sorting |
| 166 | [Insert Interval](./md_files/interval/InsertInterval.md) | Medium | Interval merging |
| 167 | [Non-overlapping Intervals](./md_files/interval/NonOverlappingIntervals.md) | Medium | Greedy |
| 168 | [Meeting Rooms](./md_files/interval/MeetingRooms.md) | Easy | Sorting |
| 169 | [Meeting Rooms II](./md_files/interval/MeetingRoomsII.md) | Medium | Heap |
| 170 | [Jump Game](./md_files/dp/JumpGame.md) | Medium | Greedy |
| 171 | [Jump Game II](./md_files/greedy/JumpGameII.md) | Medium | Greedy |
| 172 | [Gas Station](./md_files/greedy/GasStation.md) | Medium | Greedy |
| 173 | [Candy](./md_files/greedy/Candy.md) | Hard | Two-pass greedy |
| 174 | [Maximum Profit in Job Scheduling](./md_files/dp/MaximumProfitInJobScheduling.md) | Hard | DP + Binary search |

### 13. Tries, Bit Manipulation and Advanced Data Structures

| # | Problem | Difficulty | Core concept |
|---|---------|------------|--------------|
| 175 | [Implement Trie](./md_files/tree/ImplementTrie.md) | Medium | Prefix tree |
| 176 | [Design Add and Search Words](./md_files/tree/DesignAddAndSearchWords.md) | Medium | Trie + DFS |
| 177 | [Word Search II](./md_files/tree/WordSearchII.md) | Hard | Trie + Backtracking |
| 178 | [Single Number](./md_files/bit/SingleNumber.md) | Easy | XOR |
| 179 | [Single Number II](./md_files/bit/SingleNumberII.md) | Medium | Bit manipulation |
| 180 | [Counting Bits](./md_files/bit/CountingBits.md) | Easy | DP + Bits |
| 181 | [Reverse Bits](./md_files/bit/ReverseBits.md) | Easy | Bit operations |
| 182 | [Number of 1 Bits](./md_files/bit/NumberOf1Bits.md) | Easy | Brian Kernighan |
| 183 | [Missing Number](./md_files/array/MissingNumber.md) | Easy | XOR |
| 184 | [Range Sum Query - Mutable](./md_files/design/RangeSumQueryMutable.md) | Medium | Segment tree / Fenwick tree |
| 185 | [LRU Cache](./md_files/linkedlist/LRUCache.md) | Medium | HashMap + Doubly Linked List |
| 186 | [LFU Cache](./md_files/design/LFUCache.md) | Hard | HashMap + Frequency Lists |

### 14. Advanced Coding and Implementation Problems

| # | Problem | Main concept |
|---|---------|--------------|
| 187 | [Design HashMap](./md_files/collection/CustomHashMap.md) | Hashing + collision handling |
| 188 | [Design HashSet](./md_files/design/DesignHashSet.md) | Hashing |
| 189 | [Insert Delete GetRandom O(1)](./md_files/design/InsertDeleteGetRandom.md) | ArrayList + HashMap |
| 190 | [Design Hit Counter](./md_files/design/DesignHitCounter.md) | Queue + Sliding window |
| 191 | [Logger Rate Limiter](./md_files/design/LoggerRateLimiter.md) | HashMap + Time |
| 192 | [Time Based Key-Value Store](./md_files/search/TimeBasedKeyValueStore.md) | HashMap + Binary search |
| 193 | [Design Twitter](./md_files/heap/DesignTwitter.md) | Heap + HashMap |
| 194 | [All O(1) Data Structure](./md_files/design/AllOneDataStructure.md) | Doubly linked list + HashMap |
| 195 | [Design Circular Queue](./md_files/design/DesignCircularQueue.md) | Array / Circular buffer |
| 196 | [Design Skip List](./md_files/design/DesignSkipList.md) | Probabilistic data structure |
| 197 | [Implement LFU Cache](./md_files/design/LFUCache.md) | Frequency-based eviction |
| 198 | [Design a Min/Max Heap](./md_files/design/BinaryHeap.md) | Heap implementation |
| 199 | [Implement Dijkstra from Scratch](./md_files/graph/DijkstraShortestPath.md) | Graph + PriorityQueue |
| 200 | [Implement Union-Find from Scratch](./md_files/design/UnionFind.md) | Path compression + Union by Rank |

### 15. Java Concurrency, Multithreading and Core Design

| # | Problem | Main concept |
|---|---------|--------------|
| 201 | [Thread-Safe BlockingQueue](./md_files/threads/BoundedBlockingQueue.md) | ReentrantLock + Conditions (notFull, notEmpty) |
| 202 | [Print Odd and Even Alternately](./md_files/threads/PrintEvenOddUsingThreads.md) | Thread coordination using wait/notify or semaphores |
| 203 | [Custom Simple Thread Pool](./md_files/threads/SimpleThreadPool.md) | Worker thread pool with a task queue |
| 204 | [Producer-Consumer Implementation](./md_files/threads/ProducerConsumer.md) | Raw monitor synchronization (wait() / notifyAll()) |
| 205 | [Custom Read-Write Lock](./md_files/threads/CustomReadWriteLock.md) | State-based tracking for multiple readers / single writer |
| 206 | [Concurrency Coordination Puzzles](./md_files/threads/ConcurrencyCoordinationPuzzles.md) | Print in Order / FooBar sequence synchronization |
| 207 | [Thread-Safe Singleton Variations](./md_files/basic/SingletonPattern.md) | Double-Checked Locking (volatile) and Bill Pugh Holder pattern |
| 208 | [Fully Immutable Class with Mutable Fields](./md_files/basic/ImmutableClassDemo.md) | Deep defensive copying for constructors and getters |
| 209 | [Custom Functional Interface and Collector](./md_files/core/CustomCollectors.md) | Custom stream reduction aggregation logic |
| 210 | [Custom Resource Manager (AutoCloseable)](./md_files/core/ResourceManager.md) | Try-with-resources integration with exception suppression |
