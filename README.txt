Project Management

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Part 1: Trie

Class : TrieNode<T>

Data : TrieNode<T> arr[96] (array for storing nodes corresponding to characters), boolean isend (for checking whether this is the end of the word or not), T value (the data value stored at this node, and this is not null only if isend is true, i.e., we store the data only at the end of the word)

Function :
    T getValue():
        This function returns the value stored at this node.

Class : Trie<T>

Data : int height (to store the height of the trie), TrieNode<T> root (to store the root of the trie).

Space taken by the data structure : For every node, the space taken is at most the alphabet size, so the space taken by this data structure is O(number_of_characters_stored * alphabet_size) which is O(number_of_characters_stored) in our case, as the alphabet size is 95 which is constant.

Functions :

    search(String word):
        This function returns the last trie node of a given string, if it is found, else it returns null. The algorithm is iterative, and loops over the characters in the word. If the search terminates before the end of the word is reached, then we return null. Else we check whether the last node is null or not and isend entry of the node is true or not. If this happens, then we return null, else we return the last node. Time complexity for searching is O(wordsize) because the loop gets over at most by the end of the word. Space complexity is O(1).

    startsWith(String prefix):
        This function returns the last trie node associated with the prefix if it is found, else returns null. The algorithm is almost the same as the previous function, but with the last check being whether the node is null or not (we don't need to check the isend entry of the node here). Time and space complexity remain the same as the previous case.

    insert(String word):
        This function checks if we can insert the word in the trie or not, and if not, it returns false (in the case that the word is already there, which is checked using the search function). We have two cases. If the word is a prefix, then we simply change the isend and the value of the node that startsWith(word) returns. Else we know for sure that the word is not there. We iterate till we can't find a larger prefix of the word in the trie. Then we make a trie node chain till the end of the word, and set the isend and value to appropriate values. The time complexity of this function is O(wordsize) because we traverse the whole word at most a constant number of times. The space complexity of this function is O(1).

    delete(String word):
        This function deletes the given word from the trie if the word exists, if it does not, it returns false. For that, it first checks if the word is a prefix of any other word in the trie or not, by finding the number of children of the last node of the word. If it is, it simply modifies the node's isend and value. If not, it searches for the node corresponding to the rightmost character of the word which has more than one child. Then it removes the node that corresponds to the next character in the word, and we are done. The time complexity is O(alphabet_size*wordsize), and the space complexity is O(1).

    printTrie(TrieNode trieNode):
        This function prints all the words possible from this node. For this, we do an iterative dfs that checks whether the value at a node that is being visited is null or not, and if it is not, it is stored in an arraylist. Then finally the arraylist is printed. The maximum space taken by the stack used by the dfs is the depth of the subtree rooted at trieNode, which is upper bounded by the size of the largest word in the trie. The space taken by the arraylist is proportional to the output size, which is the total length of words possible from trieNode. So the space complexity is O(sum of lengths of words that are stored in this subtree). For the time complexity, the dfs processes every node of the subtree alphabet_size times for finding the children (number of children is upper bounded by the total length of words), and the printing cost is proportional to the total length of words. So the time complexity is O(alphabet_size * total length of words) in the worst case which in our case is O(total length of words), as alphabet size is a constant.

    printLevel(int level):
        This function prints all the nodes with level equal to the given level. For this, we use a modified bfs, which keeps track of the level of the nodes and the number of nodes at the ith level. For this we keep two queues, one for nodes and the other for the characters in these nodes. We have a counter i that is initially 1. Before i changes from i to i+1, we have stored all the nodes of level i in the queue we use for the modified bfs (we remove all the elements of the ith level one by one and put the children of this node in the queue and reduce the number of nodes at this level by 1, in a loop). In the end, when we have reached the last level, we transfer all the contents of the character queue in an arraylist of characters. Then we find the frequency of all the characters and sort the arraylist using counting sort (we don't add the characters which are equal to spaces though), and eventually print the arraylist. For the time complexity, we visit all the nodes till level equal to level exactly once, and do a constant amount of work with them. So the time taken by the modified bfs is proportional to the nodes till the given level (multiplied by the alphabet_size which is constant for our case). For printing the nodes at the given level, we visit every node at that level once, so the time taken is proportional to the total number of nodes at the given level. So in total, the time complexity is equal to O(alphabet_size*(total number of nodes till level = level)). Note that the queue always has at most twice the number of nodes at the ith level for any i (because it has number of nodes as < nodes of level i + nodes of level i+1), so the space complexity is equal to O(maximum number of nodes in any level till the given level).

    print():
        This function prints the whole trie. The algorithm for this function is almost the same as the previous function, but we print at every point in the loop instead of just the end. So the number of steps needed for accessing nodes (and not printing) are the same, with level replaced by the maximum height of the tree. The printing prints all the nodes in the trie, so it takes time proportional to the total number of nodes in the trie. So the total time complexity is in fact proportional to (alphabet_size * number of nodes in trie) which is upper bounded by (alphabet_size* total length of words stored in the trie). So the time complexity is O(alphabet_size * total length of words stored in the trie). The space complexity remains the same, but with the level replaced with the maximum height of the trie. So, the space complexity is O(maximum number of nodes in any level).

Class : Person

Data : String name (for storing the name of the person), String phone (for storing the phone number of that person).

Functions :
    String getName() :
        This returns the name of the person
    String toString():
        This returns the data of the Person as in the format requested in the specifications.


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Part 2 : Red Black Tree

Class : RedBlackNode<T>

Data : T key (to store the key for the node), int colour (to store the colour for the node), List<E> values (to store the values in the node), RedBlackNode<T,E> left, right, parent (to store the left child, right child and the parent respectively).

Functions :
    E getValue():
        This returns null (because we don't use it).
    List<E> getValues():
        This returns the list of values that the node stores.

Class : RBTree<T, E>

Space taken by this data structure is Theta(space taken by values stored in the tree), and is thus Theta(sum of (space taken by values at node) taken over every node).

Data : RedBlackNode<T,E> root (to store the root of the tree)

Functions :

    isBlack(RedBlackNode<T,E> node):
        This returns false if the node's colour is red, and otherwise it returns true (because by default null nodes are black)

    RedBlackNode<T, E> search(T key):
        This returns the node whose key matches with the given key, if it is found, and a node with values equal to null otherwise. For this, we iterate starting from the root and use a loop that iterates while the current node n is not null, and at every step, does one of the following : 1. Go to the left child if the node's key is more than the mentioned key, 2. Go to the right child if the node's key is less than the mentioned key, and 3. break as we found the node. Now if the node n is not null, we simply return it, else we make a new node with null data in it and return it. The complexity of this is O(log(n)*time taken in comparison) where n is the number of nodes in the tree. This is because at every step, the level either increases or the algorithm stops, and the number of levels is bounded above by 2log(n+1). The space complexity is O(1) because we used a constant amount of space in this algorithm.

    RedBlackNode<T, E> search1(T key):
        This is a helping function for the next function, and does the same job as the previous function, but returns a null node if the key is not found. The time and space complexities stay the same as the previous function.

    insert(T key, E value):
        This function inserts the key and value as given into the red black tree.
        Firstly it checks if the key is already in the tree or not, if it is, it simply appends the value to the node's values list. Else, we know that the key is not in the tree, so we need to make a new node (which is by default red) and insert it into the tree. So for this, we first check if the root is null or not. If the root is null (id est the tree is empty), we assign to the root a new node with the key as given and values as a list containing the value as given, and return. If the root is not null, we perform an ordinary bst insertion initially, by keeping track of the parent and finding where to insert the node by going down the levels till we reach a null node, and finally assigning the node as the appropriate child of the parent. Now there might be a double red problem (that is, the parent and the node might both be red). So for fixing that, we make a loop for eliminating the error by propagating it upwards or ending it somewhere in between. While we don't reach the null node or the root, and while the colour of the node is red, we do this. If the node's parent is black (this parent must exist as the root is black and this node can't be the root), we stop the loop. Else we check if the uncle exists or not. If it exists and it is red, then we recolor the uncle, parent and the grandparent and set the node equal to the node's grandparent and continue with the loop. In the other case, we are guaranteed an uncle coloured black. In this case, we rotate it suitably in 4 cases (based on whether the parent is the left or right child of the grandparent, and the subcases of these being whether the node is the left or right child of the parent), using the functions leftRot and rightRot which have a constant time complexity. When we use rotations, in each case, the colour of the grandparent becomes black, so we perform at most 1 recolouring. Eventually this loop ends and we have only one property that might not be satisfied, that is the colour of the root must be black. So we always change the colour of the root to black. So this finally keeps all the properties of the red black tree intact and we have inserted the node successfully.
        For the space complexity, we have always used a constant number of variables and references in this function, so the space complexity is constant. For the time complexity, the loop runs for at most half the distance from the node to the root (because at every step we either terminate, or replace the node with the grandparent), and we always do a constant number of operations (excluding comparisons) in every loop. Since the distance is upper bounded by the depth of the tree, which is in turn upper bounded by 2 log(n+1) where n is the number of nodes, insertion takes place in O(log(n)*time taken in comparison) time.

    leftRot(RedBlackNode<T,E> node):
        This functions changes the structure of the subtree rooted at node as shown (it assumes that the right child, say r, of node exists)

                node                     r
               /    \                   / \
              a      r        ->     node  c
                    / \             /   \
                   b   c           a     b

        Here we do a restructuring using a constant number of pointer changes, and link r to the parent of the node if node is not the root, else makes r the root.
        The space and time complexities of this function are thus O(1) for both.

    rightRot(RedBlackNode<T,E> node):
        This function changes the structure of the subtree rooted at node in a similar but symmetric manner to the previous function. (It assumes that the left child exists).
        The space and time complexities of this functionn are also O(1) for both.
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Part 3 : Priority Queue

Class : Student

Data : String name (to store the student's name), Integer marks (to store the student's marks)

Functions :

    Student(String trim, int parseInt):
        This function creates the student with the name as trim and marks as parseInt.
    compareTo(Student student):
        This function returns 0 if the marks of this student and the argument student are equal, 1 if this student has more marks and -1 otherwise.
    getName():
        This function returns the name of the student.
    getMarks():
        This function returns the marks of the student.
    Student(Student s):
        This function creates the student which is a copy of s.
    toString():
        This function returns the data of the student as a string in the form specified in the specifications.

Class : pair<T, E>

This class stores a pair of two variables of data types T and E, and is an auxiliary class for helping in the implementation of the MaxHeap<T> class.

Class : MaxHeap<T>

Space taken by this data structure is Theta(sum of space taken by every node), and in the special case where T is a data type that takes up constant amount of space equals Theta(n) where n is the number of nodes.

Data : ArrayList<pair<T, Integer> heatp (the arraylist where we store the elements of the maxheap with the time), size (where we store the size of the arraylist), time (what the time at insertion is, this increments by 1 whenever we insert an element).

Functions :

    insert(T element) :
        This function inserts the element into the heap. If the size is 0, we simply let it be the first element in the heap and return. Else, we append this to the end of the heap (and append the insertion time of this node to the times arraylist), and then bubble it up while the parent of this node does not satisfy the heap property. heap is an arraylist which has elements in such an order such that the element at the ith index has its children at the indices 2i+1 and 2i+2, and the parent at floor((i-1)/2), (parent doesn't exist for root, which is at the 0th index).
        So for bubbling up, we use an iterative algorithm, which starts from the last node, and the data at this index swaps places with the parent if the value at the parent is lesser than the data or if the insertion time of the data is lesser than that for the parent, else we are done and the loop breaks.
        For the space complexity, since we use a constant amount of space, the space complexity is O(1).
        For the time complexity, we note that at every point in the loop, we always go to index (i-1)/2 from i, and thus at every step, the index is reduced by more than half. So the number of steps before we hit the root is at most r where r is the smallest integer satisfying n/2^r < 1, i.e., r = ceil(log(n)). Since we do a constant amount of work along with a constant number of comparisons in every loop, the time complexity is O(log(n)*time taken in comparison).

    T extractMax() :
        This function removes the maximum element of the heap and returns it (if there are multiple maximum elements, it returns the one with the least insertion time), if the heap is not empty, else it returns null. We set a variable ans equal to the first element in the heap, and then copy the last element of the heap to the first element and remove the last element. Now we bubble down the first element of the heap while there is a child (more specifically the maximum child) of this element whose data is more than this or whose data is equal but the insertion time is smaller than this.
        For bubbling down, we use an iterative algorithm that is analogous to the algorithm in the bubbling up, except that we check the maximum child and check whether the condition is satisfied or not.
        For the space complexity, we use a constant amount of space, so the complexity is O(1).
        For the time complexity, we either go to the left or the right child, and hence more than double the index. In the worst case, we don't break in between and reach the last index. So the number of iterations cannot exceed 1+r where r is the smallest integer such that 2^r < n, where n is the size of the heap. Since in each iteration, we do a constant amount of work and constant amount of comparisons, the complexity is O(log(n)*time taken in comparison), where n is the size of the heap.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Part 4 : Project Management

Class : Job

Data : Project project (for storing the project), String name (for storing the job name), User user (for storing the user name), Integer time (for storing the time taken by the job), boolean iscompleted (for storing the status of the job), end_time for endtime, arrival_time, isflushed, and id.

Functions :
    comp(x, el):
        This function compares the two JobReport_ variables x and el, and works as a custom comparator function.

    merge(a, left, mid, right):
        This works for merging the subarrays of a from left to mid and from mid+1 to right into the correct order. Time complexity : O(right - left), space complexity : O(right - left)

    mergesort(a, left, right):
        This function sorts the jobs with indices from left to right in the decreasing order of priority. Time complexity is O(nlogn). Space complexity is O(n), where n is right - left.

    user():
        Returns the username of the user whose job this is.

    project_name():
        Returns the name of the project whose job it is.

    budget():
        Returns the budget of this job.

    arrival_time():
        Returns the arrival time of this job.

    completion_time():
        Returns the completion time of this job (INT_MAX if this job hasn't been executed)

    compareTo(Job job):
        This function returns the result of comparison of this job's project's priority and the given job's project's priority.

    toString():
        This function returns the data in this job in String format, according to the specification given.


Class : Project

Data : Integer priority (for the priority of the project), Integer budget (for storing the budget of the project), String name (for storing the name of the project), ArrayList<Job> projectsjobs (for storing the jobs in the projects in the order they were added in the project)

Functions :

    ArrayList<JobReport_> findjobs(int t1, int t2):
        This function performs two binary searches on the jobs of the given project to find the extremal jobs (with highest and lowest time) within t1 and t2 (with the predicate intime of job(i) <= t2 and the predicate intime of job(i) >= t2 for the two different binary searches). Then it finds the jobs with indices between the two indices found by these binary searches, and returns the jobs as an arraylist. So the time complexity is O(log (number of jobs in the project) + number of jobs with
        intimes in the given range). Space complexity is O(number of jobs with the intimes in the given range)

    ArrayList<JobReport_> findjobs(int t1, int t2, User u):
        This function does the same as the previous function, except that after the binary searches, it filters those jobs which have user as u. The time complexity is still O(log (number of jobs in the project) + number of jobs with intimes in the given range). Space complexity is O(number of jobs with the intimes in the given range and the user as u).

    mergesort(ArrayList<Project>a, int left, int right):
        This function sorts (according to the priority) the part of a which is between the indices left and right both inclusive. Time complexity is O(n log n) and Space complexity is O(n), where n is right - left. Since mergesort is stable, FIFO order is maintained.

    merge(ArrayList<Project>a, int left, int mid, int right):
        This function merges the two subranges of a from left to mid and from mid + 1 to right, and this is a helper function for the mergesort in the previous function.



Class User

Data : String name (for storing the name of the user), Integer consumedtime, Integer lasttime, ArrayList<Job> usersjobs (for the user's jobs in order of FIFO).

Function :

    compareTo(User user):
        This function returns the result of comparison of two users, first on the basis of consumed time and if they're the same, then on the basis of the last time. Time complexity O(1).

    String user():
        This returns the user's name.

    int consumed():
        This returns the consumed budget by the user.

    ArrayList<JobReport_> findjobs(int t1, int t2):
        This is totally analogous to the function by the same name in the Project class. The time complexity and space complexity are written there.

Class : Scheduler_Driver

Data : int totaljobs (to store the number of jobs at any stage), int done (to store the total number of jobs that are done at any stage), int timenow (to store the time at any stage), int unf (to store the number of unfinished jobs in the end of execution), MaxHeap<Job> jobs (for storing the jobs for processing), Trie<Job> jobtrie (for storing the jobs for easy retrieval in query), Trie<Project> projects (for storing the projects for easy retrieval), RBTree<String, Job>
unfinishedjobs (for storing unfinished jobs, with key as the project name), ArrayList<Job> finishedjobs (for storing the finished jobs), ArrayLists allusers and allprojects and alljobs for all the projects and the users and the jobs.

Functions :

    main, timed_report - This is as given in the default code.

    ArrayList<UserReport_> timed_top_consumer(int top):
        This function creates a maxheap out of all users and returns the top 'top' elements of the heap, so as to get the top 'top' consumers in the arraylist.

    void timed_flush(int waittime):
        This function performs the flush function as follows. It first makes a variable for the total job runtime of the flush operation completion. And then it makes a heap. Then while there is an element in the job heap, we remove one job from it one at a time, and then if the job is not finished, and the waiting time of the job is >= the waittime, and the budget of the project containing this job is >= the budget of this job, we execute it, and update the job's user's time
        variables, and also those for the job itself, and the budget of the project too. Then we push it in the arraylist called push that we used, and adds this to the finished jobs list. Else we just insert it into the temporary heap we made. Then in the end it finally sets the global jobs heap equal to this temporary heap. The space complexity is O(number of jobs) and the time complexity is O((n)*logn) where n is the number of jobs.

    ArrayList<JobReport_> handle_new_priority(String s):
        This function iterates over all jobs and then chooses the jobs which are the incomplete and whose priority is more than the priority given by s (which is just an integer written as a string). Time complexity is O(number of jobs) and space complexity is O(number of jobs with priority exceeding s).

    ArrayList<JobReport_> handle_new_projectuser(String[] cmd):
        This function checks whether the project and user mentioned exist, and if they do, it calls the findjobsuser function from the Project class. The complexity of this function is mentioned there. Then it sorts the jobs in this arraylist and returns the Arraylist. The time complexity is O(log(number of jobs in the project) + rlogr) where r is the number of jobs in the intersection of the job lists of the user and the project. Space complexity is O(r).

    ArrayList<JobReport_> handle_new_project(String[] cmd):
        This function checks if the project mentioned exists, and if it does, it calls the function findjobs from the user class, and then returns the ArrayList returned there. The space and time complexity are the same as that function.

    ArrayList<JobReport_> handle_new_user(String[] cmd):
        This function is completely analogous to the previous function, except the projects are replaced by the user. The space and the time complexities are mentioned in the user class's findjobs complexity.

    void schedule():
        This function executes a job by calling execute_a_job().






    handle_project(String cmd):
        This makes a project with the given specifications and inserts it into projects. Space complexity and time complexity are both O(project's name's length).

    handle_job(String[] cmd):
        This checks if the project and user with the given data exist or not, and if they do, we make a job with these values of project and user and insert it into both the jobtrie and jobs, as well as in the job lists of the projects and users. It also updates the time variables of both jobs and the users. Space complexity is O(job_name_length + project_name_length + user_name_length) and the time complexity is O(job_name_length * log(size of heap) + project_name_length + user_name_length)

    handle_user(String name):
        This makes a user with the given name and inserts it into the users trie. Space and time complexity are both O(user_name_length)

    handle_query(String key):
        This function checks whether the query is valid or not by searching for the job in the jobtrie, and if it is, prints the status of the job.
        Space complexity is O(1) and time complexity is O(job_name_lenght).

    execute_a_job():
        This function removes the jobs from the heap till we get a job that is executable, and for the unexecutable jobs, inserts jobs in the red black tree. Time complexity is O(number_of_jobs_in_heap * log number_of_jobs_in_heap), and space complexity is O(1).

    handle_add(String cmd[]):
        This function checks if the project is present or not, and if it is, it adds the mentioned budget to the budget of the project, and then empties the values at the node with the key as that project's name in the red black tree, and inserts all of them into the jobs heap. Time complexity is O(log(number of projects)*project_name_length + no.ofjobsofthiproject*log(heap size)). Space complexity is O(project_name_length).

    print_stats():
        This function prints the stats of the finished and the unfinished jobs by using a linear traversal on the finishedjobs and sorts all the projects using the mergesort that we used, and then iterate over all projects, and iterate all the jobs in all of them.  The time complexity is O(sum of lengths of project names + sum of lengths of job names + sum of lengths of user names + projects*log(projects)) and space complexity is O(1).

    run_to_completion():
        This function executes the remaining jobs and checks whether the jobs are completed successfully or not, in a way similar to the handle_empty_line() method. The time complexity is O(number_of_jobs_in_heap * log number_of_jobs_in_heap) and space complexity is O(1).

    timed_run_to_completion():
        This function is the timed version of the previous function, without any print statements.

    timed_handle_project():
        This function is the timed version of the handle_project function, without any print statements.

    timed_handle_job():
        This function is the timed version of the handle_job function, without any print statements.

    timed_handle_user():
        This function is the timed version of the handle_user function, without any print statements.

    handle_empty_line():
        This function calls the schedule() function.
