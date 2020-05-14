package com.example.carepet.entity;

public class Communitys {
        private String title;//标题
        private String imgjson;//内容图
        private String content;//n内容
        private int userId;//用户ID
        private String name;//
        private String headName;//
        private String time;//发布时间
        private String city;//城市
        private String tag;//puppy 和experience
        private int flag;//0和1

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
            return "Communitys [ title=" + title + ", imgjson=" + imgjson + ", content="
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

}

