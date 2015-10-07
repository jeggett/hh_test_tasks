Task 1
Problem:
Given set of N points on a plane. Find pair of closest points;

SOLUTION:
We ompute a closest pair of points in a set of N points in the plane and provide methods
for getting the closest pair of points and the distance between them.

This implementation uses a divide-and-conquer algorithm. It runs in O(N log N)
time in the worst case and uses O(N) extra space. The steps of the algorithm
are following:

ALGORITHM:
1. Sort points according to their x-coordinates.
2. Split the set of points into two equal-sized subsets by a vertical line x=xmid.
3. Solve the problem recursively in the left and right subsets. This yields the
left-side and right-side minimum distances dLmin and dRmin, respectively.
4. Find the minimal distance dLRmin among the set of pairs of points in which
one point lies on the left of the dividing vertical and the second point lies to the right.
5. The final answer is the minimum among dLmin, dRmin, and dLRmin.


Task 2
Problem:
Given a finite sequence of natural numbers. Determine wether it is possible to divide the numbers into two subsets such as totals of both subsets are equal. Show one variant of such distribution. Is there a subset of initial set with total of 100.

SOLUTION:
The problem is quivalent to the  the two-way partitioning problem:
Given a set of numbers, it's need to
divide them into two subsets, so that the sum of the numbers in
each subset are as nearly equal as possible. The problem is NPcomplete,
and is contained in many scheduling applications.

Most algorithms for NP-complete problems can be divided into
two classes: Complete algorithms that are guaranteed to find an optimal
solution eventually, but that run in exponential time, and polynomial-time
algorithms that only find approximate solutions.

ALGORITHM:
Firstly we check is it possible to partition given set with complexity O(sum*n).
Following are the two main steps to solve this problem:
1) Calculate sum of the array. If sum is odd, there can not be two subsets with equal sum, so return false.
2) If sum of array elements is even, calculate sum/2 and find a subset of array with sum equal to sum/2.

Following is the recursive property of the second step mentioned above.
Let isSubsetSum(arr, n, sum/2) be the function that returns true if 
there is a subset of arr[0..n-1] with sum equal to sum/2
The isSubsetSum problem can be divided into two subproblems
 a) isSubsetSum() without considering last element 
    (reducing n to n-1)
 b) isSubsetSum considering the last element 
    (reducing sum/2 by arr[n-1] and n to n-1)
If any of the above the above subproblems return true, then return true. 
isSubsetSum (arr, n, sum/2) = isSubsetSum (arr, n-1, sum/2) ||
                              isSubsetSum (arr, n-1, sum/2 - arr[n-1])
If it possible to partition initial set than we will find such partition
as follows:

The most obvious algorithm for finding optimal solutions is to compute all
possible subset sums, and return the subset whose sum is closest to one-half of the sum of all the elements. If there are n elements to be partitioned, the time complexity of this algorithm is O(2^n), since there are 2n subsets of an n-element set.

Horowitz and Sahni showed how to dramatically reduce the running time
of this algorithm by trading space for time, as follows: Arbitrarily divide
the original set of n numbers into two subsets, each containing n^2 numbers.
For example, if we start with the set (4; 5; 6; 7; 8), we might divide it into the
subsets (4; 6; 8) and (5; 7). For each of the two subsets, compute and store
all the possible subset sums achievable using numbers from only that subset.
This would give us the subset sums (0; 4; 6; 8; 10; 12; 14; 18) and (0; 5; 7; 12).
Sort these lists of subset sums. Every subset sum from the original set can be
achieved by adding together a subset sum from one of these lists to a subset
sum from the other. If a subset sum comes entirely from numbers in one of
the lists, the value added from the other list is simply zero, for the subset
sum of the null set. Our target value is a subset sum closest to half the sum
of all the original numbers, which is 15 in this case.

In order to recover initial subsets from subset sums custom pair
(sumOfSubset, subset) type is used.

We maintain a pointer into each of the sorted lists of subset sums. The
pointer into one of the lists starts at the smallest element, say 0 on the first
list, and only increases, while the pointer into the other list starts at the
largest element, 12 in this case, and only decreases. If the sum of the two
numbers currently pointed to, 0+ 12 = 12 in this case, is less than the target,
15 in this case, increase the pointer that is allowed to increase. This would
give us 4 + 12 = 16 in this case. If the sum of the two numbers pointed to
is greater than the target, as it is now, decrease the pointer that is allowed
to decrease, giving us 4 + 7 = 11 in this case. Since 11 is less than 15, we
increase the increasing pointer, giving us 6 + 7 = 13. Since 13 is still low, the
next step gives us 8 + 7 = 15, which is exactly the target, and terminates the
algorithm. In general, we remember the subset sum closest to the target, and
return that if we don't find a perfect partition.

Since each of the two lists of numbers is of length n/2, generating all their
subset sums takes O(2^(n/2)) time. They can be sorted in 
O(n*2^(n/2)) time. Finally, the two lists of subset sums are scanned in linear time,
for an overall time complexity of O(n2n=2) time. In fact, this algorithm can
be improved by generating the lists of subset sums in sorted order initially,
resulting in a running time of O(2^(n/2)).

The main drawback of this algorithm is the space needed to store the lists
of subset sums. Each list is of size O(2^(n/2)), so the overall space complexity
is O(2(n^2)).

Finally knowing that subset sum problem is the special case of partition problem,
 in which subset sum is half of the sum of all elements in the set. We can just add 
 value=sum-2*100 to initial set and, then partition it into two equal subsets.
