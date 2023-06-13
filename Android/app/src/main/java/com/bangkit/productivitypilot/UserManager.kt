object UserManager {
    private var userId: String? = null
    private var username: String? = null
    private var name: String? = null
    private val followingList: MutableList<String> = mutableListOf()

    fun setUserId(userId: String) {
        this.userId = userId
    }

    fun getUserId(): String? {
        return userId
    }

    fun saveUserInformation(username: String?, name: String?) {
        this.username = username
        this.name = name
    }

    fun getUsername(): String? {
        return username
    }

    fun getName(): String? {
        return name
    }

    fun getFollowingList(): List<String> {
        return followingList
    }

    fun addToFollowingList(userId: String) {
        followingList.add(userId)
    }

    fun removeFromFollowingList(userId: String) {
        followingList.remove(userId)
    }
}
