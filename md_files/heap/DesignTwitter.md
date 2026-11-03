# Design Twitter

Design a simplified Twitter where users can post tweets, follow and unfollow other users, and see the 10 most recent tweets in their news feed (tweets of themselves and of the users they follow, newest first). Implement `postTweet(userId, tweetId)`, `getNewsFeed(userId)`, `follow(followerId, followeeId)` and `unfollow(followerId, followeeId)`.

## Algorithm Strategy (Per-User Tweet Lists + Max-Heap Merge)

1. Give every tweet a global, increasing timestamp. Store each user's tweets in a list in posting order (so the newest tweet is last) and each user's followees in a set.
2. `postTweet`: append `(timestamp, tweetId)` to the list of the user. `follow` / `unfollow`: add or remove the followee (a user cannot unfollow himself, because his own tweets are always in the feed).
3. `getNewsFeed`: this is a merge of several sorted lists. Put the newest tweet of the user and of every followee into a max-heap ordered by timestamp.
4. Remove the top 10 times; each time a tweet is removed, push the previous tweet of the same user. The removed tweets, in order, are the feed.

## Example walkthrough

- User 1 posts tweet 5: the feed of user 1 is `[5]`. User 1 follows user 2, user 2 posts tweet 6: the feed is `[6, 5]` (6 is newer).
- After user 1 unfollows user 2 the feed is `[5]` again.

## Complexity Analysis

- **Time Complexity:** `O(1) for post / follow / unfollow, O(F log F) for getNewsFeed` — `F` is the number of followed users plus one (at most 10 tweets are returned).
- **Space Complexity:** `O(total tweets + follows)` — The stored tweets and relationships.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Scan the Global Tweet Log Backwards

Append every tweet to one global list. To build a feed, scan that list from the newest tweet backwards and keep the tweets whose author is the user or a followee, until 10 are found. Simple, but the scan may have to cross the tweets of unrelated users.

```java
static class LogTwitter {
    private final List<int[]> log = new ArrayList<>();                       // {userId, tweetId}
    private final Map<Integer, Set<Integer>> followees = new HashMap<>();

    public void postTweet(int userId, int tweetId) { log.add(new int[]{userId, tweetId}); }

    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> visible = new HashSet<>(followees.getOrDefault(userId, Collections.emptySet()));
        visible.add(userId);
        List<Integer> feed = new ArrayList<>();
        for (int i = log.size() - 1; i >= 0 && feed.size() < 10; i--) {
            if (visible.contains(log.get(i)[0])) feed.add(log.get(i)[1]);
        }
        return feed;
    }

    public void follow(int followerId, int followeeId) { followees.computeIfAbsent(followerId, id -> new HashSet<>()).add(followeeId); }
    public void unfollow(int followerId, int followeeId) { followees.getOrDefault(followerId, new HashSet<>()).remove(followeeId); }
}
```

- **Time Complexity:** post O(1), getNewsFeed O(total tweets)
- **Space Complexity:** O(total tweets)

### Approach 2: Collect the Last 10 Tweets of Each User and Sort

Only the 10 newest tweets of every relevant user can appear in a feed. Gather them all, sort by time (newest first) and keep the first 10. No heap, but it sorts up to `10 * F` tweets.

```java
static class SortTwitter {
    private int clock;
    private final Map<Integer, List<int[]>> tweets = new HashMap<>();         // {time, tweetId}
    private final Map<Integer, Set<Integer>> followees = new HashMap<>();

    public void postTweet(int userId, int tweetId) { tweets.computeIfAbsent(userId, id -> new ArrayList<>()).add(new int[]{clock++, tweetId}); }

    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> users = new HashSet<>(followees.getOrDefault(userId, Collections.emptySet()));
        users.add(userId);
        List<int[]> candidates = new ArrayList<>();
        for (int user : users) {
            List<int[]> list = tweets.getOrDefault(user, Collections.emptyList());
            candidates.addAll(list.subList(Math.max(0, list.size() - 10), list.size()));
        }
        candidates.sort((a, b) -> b[0] - a[0]);
        List<Integer> feed = new ArrayList<>();
        for (int i = 0; i < Math.min(10, candidates.size()); i++) feed.add(candidates.get(i)[1]);
        return feed;
    }

    public void follow(int followerId, int followeeId) { followees.computeIfAbsent(followerId, id -> new HashSet<>()).add(followeeId); }
    public void unfollow(int followerId, int followeeId) { followees.getOrDefault(followerId, new HashSet<>()).remove(followeeId); }
}
```

- **Time Complexity:** getNewsFeed O(F * 10 log(F * 10))
- **Space Complexity:** O(total tweets)

### Approach 3: Max-Heap Merge of the Users' Tweet Lists (Optimized)

The tweet lists of the users are already ordered by time, so a feed is a k-way merge: keep the newest tweet of every relevant user in a max-heap and repeatedly take the top, pushing the previous tweet of the same user. At most 10 tweets are produced. This is the version implemented in the repository.

```java
static class HeapTwitter {
    private int clock;
    private final Map<Integer, List<int[]>> tweets = new HashMap<>();         // {time, tweetId}
    private final Map<Integer, Set<Integer>> followees = new HashMap<>();

    public void postTweet(int userId, int tweetId) { tweets.computeIfAbsent(userId, id -> new ArrayList<>()).add(new int[]{clock++, tweetId}); }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> tweets.get(b[0]).get(b[1])[0] - tweets.get(a[0]).get(a[1])[0]);
        Set<Integer> users = new HashSet<>(followees.getOrDefault(userId, Collections.emptySet()));
        users.add(userId);
        for (int user : users) {
            List<int[]> list = tweets.get(user);
            if (list != null && !list.isEmpty()) heap.add(new int[]{user, list.size() - 1});
        }
        List<Integer> feed = new ArrayList<>();
        while (!heap.isEmpty() && feed.size() < 10) {
            int[] entry = heap.poll();
            feed.add(tweets.get(entry[0]).get(entry[1])[1]);
            if (entry[1] > 0) heap.add(new int[]{entry[0], entry[1] - 1});
        }
        return feed;
    }

    public void follow(int followerId, int followeeId) { followees.computeIfAbsent(followerId, id -> new HashSet<>()).add(followeeId); }
    public void unfollow(int followerId, int followeeId) { followees.getOrDefault(followerId, new HashSet<>()).remove(followeeId); }
}
```

- **Time Complexity:** getNewsFeed O(F log F + 10 log F)
- **Space Complexity:** O(total tweets)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Scan the Global Tweet Log Backwards | post O(1), getNewsFeed O(total tweets) | O(total tweets) |
| 2 | Collect the Last 10 Tweets of Each User and Sort | getNewsFeed O(F * 10 log(F * 10)) | O(total tweets) |
| 3 | Max-Heap Merge of the Users' Tweet Lists (Optimized) | getNewsFeed O(F log F + 10 log F) | O(total tweets) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### DesignTwitter.java

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DesignTwitter {

    private static class Tweet {
        final int time;
        final int id;

        Tweet(int time, int id) {
            this.time = time;
            this.id = id;
        }
    }

    private int clock;
    private final Map<Integer, List<Tweet>> tweets = new HashMap<>();
    private final Map<Integer, Set<Integer>> followees = new HashMap<>();

    public static void main(String[] args) {
        DesignTwitter twitter = new DesignTwitter();
        twitter.postTweet(1, 5);
        System.out.println("Input  : postTweet(1, 5), getNewsFeed(1)");
        System.out.println("Output : " + twitter.getNewsFeed(1));
        twitter.follow(1, 2);
        twitter.postTweet(2, 6);
        System.out.println("Input  : follow(1, 2), postTweet(2, 6), getNewsFeed(1)");
        System.out.println("Output : " + twitter.getNewsFeed(1));
        twitter.unfollow(1, 2);
        System.out.println("Input  : unfollow(1, 2), getNewsFeed(1)");
        System.out.println("Output : " + twitter.getNewsFeed(1));
    }

    public void postTweet(int userId, int tweetId) {
        tweets.computeIfAbsent(userId, id -> new ArrayList<>()).add(new Tweet(clock++, tweetId));
    }

    public List<Integer> getNewsFeed(int userId) {
        // entries: {user, index of the tweet in the list of the user}; ordered by the timestamp of that tweet, newest first
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> tweets.get(b[0]).get(b[1]).time - tweets.get(a[0]).get(a[1]).time);

        Set<Integer> users = new HashSet<>(followees.getOrDefault(userId, Collections.emptySet()));
        users.add(userId);
        for (int user : users) {
            List<Tweet> list = tweets.get(user);
            if (list != null && !list.isEmpty()) {
                heap.add(new int[]{user, list.size() - 1});
            }
        }

        List<Integer> feed = new ArrayList<>();
        while (!heap.isEmpty() && feed.size() < 10) {
            int[] entry = heap.poll();
            feed.add(tweets.get(entry[0]).get(entry[1]).id);
            if (entry[1] > 0) {
                heap.add(new int[]{entry[0], entry[1] - 1});        // the previous (older) tweet of the same user
            }
        }
        return feed;
    }

    public void follow(int followerId, int followeeId) {
        followees.computeIfAbsent(followerId, id -> new HashSet<>()).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        Set<Integer> set = followees.get(followerId);
        if (set != null) {
            set.remove(followeeId);
        }
    }
}
```
