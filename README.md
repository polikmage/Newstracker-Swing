# newstracker
REST api based on Google News web site and using Google script for translation.
It is skeleton for future autonomous News Article data mining, classification and processing.
API has two endpoints:
1) /api-news/articles  -> Gets sorted articles from Google News Api by newest first
2) /web-news/articles -> Gets articles from Google News Web Site, sorted by Newest. Then if it was non CZ/SK article it performs translation to english.
