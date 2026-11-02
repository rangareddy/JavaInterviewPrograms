# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Java interview programs (clone of github.com/rangareddy/JavaInterviewPrograms, branch `master`). Each program is a standalone class under `src/main/java/com/ranga/<category>/` (`array`, `basic`, `collection`, `dp`, `search`, `sort`, `string`, `threads`, `tree`). There is no shared code between classes, no test suite, and no external dependencies.

Every program has a class-level Javadoc (summary, detailed `Algorithm Strategy` ordered list, `Example walkthrough`, `Complexity Analysis`, `@author Ranga Reddy`; see `array/ProductOfArrayExceptSelf.java` for the reference style) and a write-up in `md_files/<category>/<ClassName>.md`. Every `main` prints its sample data the same way as `array/MoveZerosAndOnes.java`: an aligned input line and an output line (`Original Array : ...` / `Sorted Array   : ...` or `Input  : ...` / `Output : ...`).

A write-up repeats the Javadoc as markdown, then adds an `Approaches` section (brute force to optimized, each with code and time/space complexity, a comparison table and an `Optimized Solution` note) and a `Source Code` section embedding the class without its `package` line and class Javadoc. The code in `Approaches` is not part of `src`, so compile and run any snippet you add or change before committing it. Several pages describe an optimized variant that differs from the class in `src`; the page says which approach the repository implements. Re-sync the page whenever a class changes. Name exceptions: `basic/Fibonacci` is covered by `md_files/basic/Fibonaci.md` (note the spelling) together with `dp/Fibonacci`, and `basic/ImmutableClassDemo` by `md_files/basic/ImmutableClass.md`.

`README.md` is the index: programs are grouped by category and link to their `.md` write-up. When adding or moving a program, update the package, its Javadoc, its `md_files` page and the README entry.

## Build and run

```
mvn compile                                        # compile into target/classes
java -cp target/classes com.ranga.array.TwoSum     # run one class by its fully qualified name
```

Compiler level is set via `maven.compiler.source`/`target` (8) in `pom.xml`; keep code Java 8 compatible. There are no tests or lint configuration. Each class is exercised by its own `public static void main` with hard-coded inputs.

## Workflow

Changes go through a feature branch and PR against `master` (merged with merge commits), not direct pushes.
