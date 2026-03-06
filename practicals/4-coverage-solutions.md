# COM3529 Practical Session 4: Test Coverage

# Solutions

### Questions 1 and 2: Behaviours not tested and their tests

a) Listing users following registration. See `shouldHaveUserNameFollowingRegistering`.

b) Trying to register a user who already exists. See
`shouldNotRegisterUsernameThatAlreadyExists` and `shouldNotRegisterEmailAddressThatAlreadyExists`.

c) Successfully changing username. See `shouldSuccessfullyChangeUsername`.

d) Trying to change username to one that already exists fails. See `shouldNotSuccessfullyChangeUsernameIfAlreadyInUse`.

e) Trying to change username of a user that does not exist fails. See
`shouldNotSuccessfullyChangeUsernameIfUserDoesNotExist`.

f) Users should be able to login. See `shouldLoginUser`.

g) Users should be able to logout. See `shouldLogoutUser`.

h) Should be able to get posts, and get these by user. See `shouldGetPosts`.

### Question 3

There are other behavioural corner cases / combinations and sequences of events
that we might want to test – they are not all revealed by coverage. 

For example, trying to log out a user that does not exist, not being able to
post for a user who does not exist, and so on. 

### Question 4

It is tempting to write tests that just execute code, rather than properly
testing it. Maybe you did this. For example, we don't actually need to write any
assertions to get full coverage. But then we are not properly testing it!

### Discussion Questions

#### 1. Do you think statement coverage is a useful concept in practice? 

It's useful to see what the tests "miss". It is not always a reliable method of
identifying tests that need to be written, or behaviours that have been
"missed", since it may not capture them all.

Statement coverage is a metric that can be "gamed". We can get high statement
coverage easily without writing very useful tests.

#### 2. What do you think statement coverage says about:

##### a) how many bugs will be found?

Full statement coverage will guarantee all defects are executed, but does not
guarantee they will cause infections or failures, and that those failures will
be detected by tests. (Recall the RIPR model from lecture 1.)

##### b) how many bugs will be left, if you get full coverage?

It says nothing! (See previous answer)

##### c) how much testing has been done?

Since the metric can be gamed it is unreliable with respect to telling us how
much testing has been done.

##### d) when to stop testing?

Because of all the previous answers, it is not reliable in telling us when to
stop testing. 

#### 3. Do you think 100% statement coverage is a realistic and useful goal?

It is potentially useful if approached with the right mindset; i.e. not to
concentrate on the raw percentage of coverage and using it to see what has been
missed. It is not always possible to execute all statements with automated
testing. Some statements are arguably not useful to test - e.g. `get` and `set`
methods. 
