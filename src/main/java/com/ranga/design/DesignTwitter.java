package com.ranga.design;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Design a simplified Twitter where users can post tweets, follow and unfollow other users, and see the 10 most recent tweets in their news feed (tweets of themselves and of the users they follow, newest first). Implement {@code postTweet(userId, tweetId)}, {@code getNewsFeed(userId)}, {@code follow(followerId, followeeId)} and {@code unfollow(followerId, followeeId)}.
 *
 * <p><b>Algorithm Strategy (Per-User Tweet Lists + Max-Heap Merge):</b>
 * <ol>
 *   <li>Give every tweet a global, increasing timestamp. Store each user's tweets in a list in posting order (so the newest tweet is last) and each user's followees in a set.</li>
 *   <li>{@code postTweet}: append {@code (timestamp, tweetId)} to the list of the user. {@code follow} / {@code unfollow}: add or remove the followee (a user cannot unfollow himself, because his own tweets are always in the feed).</li>
 *   <li>{@code getNewsFeed}: this is a merge of several sorted lists. Put the newest tweet of the user and of every followee into a max-heap ordered by timestamp.</li>
 *   <li>Remove the top 10 times; each time a tweet is removed, push the previous tweet of the same user. The removed tweets, in order, are the feed.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>User 1 posts tweet 5: the feed of user 1 is {@code [5]}. User 1 follows user 2, user 2 posts tweet 6: the feed is {@code [6, 5]} (6 is newer).</li>
 *   <li>After user 1 unfollows user 2 the feed is {@code [5]} again.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) for post / follow / unfollow, O(F log F) for getNewsFeed} — {@code F} is the number of followed users plus one (at most 10 tweets are returned).</li>
 *   <li><b>Space Complexity:</b> {@code O(total tweets + follows)} — The stored tweets and relationships.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
