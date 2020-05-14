package com.carepet.entity;

public class Communitys {
	    private String title;
	    private String imgjson;
	    private String content;
	    private int userId;
	    private String name;
	    private String headName;
	    private String time;
	    private String city;
	    private String tag;
	    private int flag;
	    
	    public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getHeadName() {
			return headName;
		}

		public void setHeadName(String headName) {
			this.headName = headName;
		}

	    public String getImgjson() {
			return imgjson;
		}

		public void setImgjson(String imgjson) {
			this.imgjson = imgjson;
		}

		public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }


	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }

	    public int getUserId() {
	        return userId;
	    }

	    public void setUserId(int userId) {
	        this.userId = userId;
	    }

	    public String getTime() {
	        return time;
	    }

	    public void setTime(String time) {
	        this.time = time;
	    }

	    public String getCity() {
	        return city;
	    }

	    public void setCity(String city) {
	        this.city = city;
	    }

	    public String getTag() {
	        return tag;
	    }

	    public void setTag(String tag) {
	        this.tag = tag;
	    }

	    public int getFlag() {
	        return flag;
	    }

	    public void setFlag(int flag) {
	        this.flag = flag;
	    }

		@Override
		public String toString() {
			return "Communitys [ title=" + title + ", imgjson=" + imgjson + ", pic="+ ", content="
					+ content + ", userId=" + userId + ", name=" + name + ", headName=" + headName + ", time=" + time
					+ ", city=" + city + ", tag=" + tag + ", flag=" + flag + "]";
		}

		public Communitys(String title, String imgjson,String content, int userId, String name,
				String headName, String time) {
			super();
			this.title = title;
			this.imgjson = imgjson;
			this.content = content;
			this.userId = userId;
			this.name = name;
			this.headName = headName;
			this.time = time;
		}

		public Communitys() {
			// TODO Auto-generated constructor stub
		}
	    
}
