Facebook Lite Business Rules
    1. User can only delete own account.
    2. User can only update own account details.
    3. User can only read own account details.
    4. User can read all posts available.
    5. User can only update own post.
    6. User can only delete own post.
    7. User can create own post.
    8. User can create account.

3 TRANSACTIONS
  ---> deleteUser() must update both tblusers and tblposts at the same time. Need ma delete ang posts sa this user sa tblposts.
  ---> createTable() creates 2 tables at the same time. Need nila 2 either mo success both or fail both.
  ---> loadPosts() need to read two tables at the same time (username kinsa nag post, post details). Need both or fail both

Note!!
  ---> Run Facebook class Application. Ayaw ang Hello Application.