# Android-Paging-Library-Room-Java

This is a conversion of kotlin project from <a href="https://codelabs.developers.google.com/codelabs/android-paging/">Paging Codelab</a>
to java.<br><br>
<h2>Project Overview</h2>
<p>The app allows you to search GitHub for repositories whose name or description contains a specific word. The list of repositories is displayed, in descending order based on the number of stars, then by the name. The database is the source of truth for data that is displayed by the UI, and it's backed by network requests.  </p>
<p>The list of repositories, by name, is retrieved via a <code>LiveData</code> object in <code>RepoDao.reposByName</code>. Whenever new data from the network is inserted into the database, the <code>LiveData</code> will emit again with the entire result of the query. </p>
<p>The app follows the architecture recommended in the &#34;<a href="https://developer.android.com/topic/libraries/architecture/guide.html" target="_blank">Guide to App Architecture</a>&#34;, using Room as local data storage. Here&#39;s what you will find in each package: </p>
<ul>
<li><strong>api</strong> - contains Github API calls, using Retrofit </li>
<li><strong>db</strong> - database cache for network data</li>
<li><strong>data</strong> - contains the repository class, responsible for triggering API requests and saving the response in the database</li>
<li><strong>ui</strong> - contains classes related to displaying an <code>Activity</code> with a <code>RecyclerView</code> </li>
<li><strong>model </strong>- contains the <code>Repo</code> data model, which is also a table in the Room database; and <code>RepoSearchResult</code>, a class that is used by the UI to observe both search results data and network errors </li>
</ul>
