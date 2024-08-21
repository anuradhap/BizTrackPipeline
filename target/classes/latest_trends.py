from pytrends.request import TrendReq
import json

def fetch_latest_trends():
    pytrends = TrendReq(hl='en-US', tz=360)
    trending_searches_df = pytrends.trending_searches(pn='united_states')
    trends = trending_searches_df[0].tolist()
    return trends

if __name__ == "__main__":
    trends = fetch_latest_trends()
    print(json.dumps(trends))
