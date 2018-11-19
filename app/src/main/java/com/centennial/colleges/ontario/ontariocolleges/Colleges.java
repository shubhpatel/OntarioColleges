package com.centennial.colleges.ontario.ontariocolleges;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Colleges {

    @SerializedName("colleges")
    List<College> collegeList;

    public Colleges() {
    }

    public List<College> getCollegeList() {
        return collegeList;
    }

    public void setCollegeList(List<College> collegeList) {
        this.collegeList = collegeList;
    }

    public class College {

        String name;
        @SerializedName("url")
        String logo_url;
        @SerializedName("Branch")
        List<Branch> branches;

        public College() {
        }

        public College(String name, String logo_url, List<Branch> branches) {
            this.name = name;
            this.logo_url = logo_url;
            this.branches = branches;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public List<Branch> getBranches() {
            return branches;
        }

        public void setBranches(List<Branch> branches) {
            this.branches = branches;
        }

        public class Branch {
            String branchname;
            String address;
            Float lng;
            Float lat;

            public Branch() {
            }

            public Branch(String branchname, String address, Float lng, Float lat) {
                this.branchname = branchname;
                this.address = address;
                this.lng = lng;
                this.lat = lat;
            }

            public String getBranchname() {
                return branchname;
            }

            public void setBranchname(String branchname) {
                this.branchname = branchname;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public Float getLng() {
                return lng;
            }

            public void setLng(Float lng) {
                this.lng = lng;
            }

            public Float getLat() {
                return lat;
            }

            public void setLat(Float lat) {
                this.lat = lat;
            }
        }
    }
}
