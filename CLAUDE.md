# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Java interview programs (clone of github.com/rangareddy/JavaInterviewPrograms, branch `master`). Each program is a standalone class under `src/main/java/com/ranga/<category>/` (`array`, `backtracking`, `basic`, `bit`, `collection`, `dp`, `graph`, `greedy`, `heap`, `interval`, `linkedlist`, `math`, `matrix`, `search`, `sort`, `stack`, `string`, `threads`, `tree`). There is no shared code between classes, no test suite, and no external dependencies.

Every program has a class-level Javadoc (summary, detailed `Algorithm Strategy` ordered list, `Example walkthrough`, `Complexity Analysis`, `@author Ranga Reddy`; see `array/ProductOfArrayExceptSelf.java` for the reference style) and a write-up in `md_files/<category>/<ClassName>.md`. Every `main` prints its sample data the same way as `array/MoveZerosAndOnes.java`: an aligned input line and an output line (`Original Array : ...` / `Sorted Array   : ...` or `Input  : ...` / `Output : ...`).

A write-up repeats the Javadoc as markdown, then adds an `Approaches` section (brute force to optimized, each with code and time/space complexity, a comparison table and an `Optimized Solution` note) and a `Source Code` section embedding the class without its `package` line and class Javadoc. The code in `Approaches` is not part of `src`, so compile and run any snippet you add or change before committing it. Several pages describe an optimized variant that differs from the class in `src`; the page says which approach the repository implements. Re-sync the page whenever a class changes. Every `md_files/<category>/<Name>.md` has a matching `<Name>.java` in the same-named package (apart from the helper classes `ListNode` and `TreeNode`, which have no page); keep that one-to-one mapping.

`README.md` is the index: programs are grouped by category and link to their `.md` write-up. When adding or moving a program, update the package, its Javadoc, its `md_files` page and the README entry.

### Blind 75

The Blind 75 problems are ordinary members of the category packages and folders (`array`, `matrix`, `string`, `linkedlist`, `tree`, `heap`, `graph`, `dp`, `interval`, `bit`), for example `src/main/java/com/ranga/graph/CourseSchedule.java` and `md_files/graph/CourseSchedule.md`. Problems that already existed elsewhere (Two Sum, Product of Array Except Self, Merge Intervals, Longest Substring Without Repeating Characters, Longest Palindromic Substring, Valid Anagram) and Merge k Sorted Lists (listed under both Linked List and Heap) are not duplicated; the README lists them once under their category and links to the existing write-up. Same conventions as above (Javadoc with the problem statement, aligned `Input  :` / `Output :` lines, optimal solution, `Approaches` section). Those approach snippets are not part of `src`; compile and run them (for example against the repository class on several inputs) before committing a change. `linkedlist/ListNode` and `tree/TreeNode` are shared helpers: `of(...)` builds a structure from LeetCode-style data (level order with `null` for trees) and `toString(...)` prints it.

### NeetCode 150

The NeetCode 150 problems that are not in the Blind 75 list or elsewhere in the repository (75 of them) are also ordinary members of the category packages, for example `src/main/java/com/ranga/stack/MinStack.java` and `md_files/backtracking/NQueens.md`; this added the packages `stack`, `backtracking`, `greedy` and `math`. The README lists them under their category like every other program (it has no separate Blind 75 or NeetCode sections). Same conventions as the Blind 75 problems (Javadoc with the problem statement, aligned `Input  :` / `Output :` lines, `Approaches` section whose snippets are compiled and compared with the repository class before committing). Problems that were already in the repository are not duplicated.

## Build and run

```
mvn compile                                        # compile into target/classes
java -cp target/classes com.ranga.array.TwoSum     # run one class by its fully qualified name
```

Compiler level is set via `maven.compiler.source`/`target` (8) in `pom.xml`; keep code Java 8 compatible. There are no tests or lint configuration. Each class is exercised by its own `public static void main` with hard-coded inputs.

## Workflow

Work happens on a local feature branch with local commits. Claude never pushes, opens or merges pull requests, or deletes remote branches (see the global `~/.claude/CLAUDE.md`); the user does that themselves (merge commits into `master`, feature branches deleted afterwards).
