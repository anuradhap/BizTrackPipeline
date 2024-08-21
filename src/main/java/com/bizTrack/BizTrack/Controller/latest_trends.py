from trends.request import TrendReq
import json

def get_latest_trends():
    trends = TrendReq(hl='en-US', tz=360)
    trending_searches_df = trends.trending_searches()
    trending_searches = trending_searches_df[0].todolist()
    return json.dumps(trending_searches)

if __name__ == "__main__":
    trends = get_latest_trends()
    print(trends)
