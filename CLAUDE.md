# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Java interview programs (clone of github.com/rangareddy/JavaInterviewPrograms, branch `master`). Each program is a standalone class under `src/main/java/com/ranga/<category>/` (`array`, `basic`, `dp`, `search`, `sort`, `string`, `threads`). There is no shared code between classes, no test suite, and no external dependencies.

Every program has a class-level Javadoc (summary, `Algorithm Strategy` ordered list, `Complexity Analysis` list, `@author Ranga Reddy`; see `array/ProductOfArrayExceptSelf.java` for the reference style) and a write-up in `md_files/<category>/<ClassName>.md`. A write-up repeats the Javadoc as markdown (strategy, complexity) and embeds the full source without its `package` line, so re-sync the page whenever a class changes. Exceptions: `basic/Fibonacci` is covered by `md_files/basic/Fibonaci.md` (note the spelling), and `Anagram`, `ImmutableClass`, `SingleLinkedListDemo` and `BinarySearchTree` have write-ups only, with no Java in `src`.

`README.md` is the index: programs are grouped by category and link to their `.md` write-up. When adding or moving a program, update the package, its Javadoc, its `md_files` page and the README entry.

## Build and run

```
mvn compile                                        # compile into target/classes
java -cp target/classes com.ranga.array.TwoSum     # run one class by its fully qualified name
```

Compiler level is set via `maven.compiler.source`/`target` (8) in `pom.xml`; keep code Java 8 compatible. There are no tests or lint configuration. Each class is exercised by its own `public static void main` with hard-coded inputs.

## Workflow

Changes go through a feature branch and PR against `master` (merged with merge commits), not direct pushes.
