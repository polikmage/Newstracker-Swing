# newstracker
REST api based on Google News web site and using Google script for translation.
It is skeleton for future autonomous News Article data mining, classification and processing.
API has two main endpoints:
1) GET /user/api-news/articles  -> Gets sorted articles from Google News Api by newest first
2) GET /user/web-news/articles -> Gets articles from Google News Web Site, sorted by Newest. There is possible to use translation into english.

Other endpoints are:

POST /user/watchdog -> it is possible to set watchdog, so app automatically sends new articles with chosen topics to users email

GET /user/watchdog -> gets all watchdog of current user

POST /sign_up -> to use /user/* endpoints, user must sign up sending json body:
{
    "username":"xxx",
    "password":"yyy",
    "email":"zzz"
}

POST /login -> After sign up, user can login, to log in there is needed to send json body such as : 
{
    "username":"xxx",
    "password":"yyy"
}

Then the app responds with bearer JWT token which must be present in each /user/* request
