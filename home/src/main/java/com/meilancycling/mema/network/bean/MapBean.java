package com.meilancycling.mema.network.bean;

import java.util.List;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/25 11:33 AM
 */
public class MapBean {


    /**
     * type : FeatureCollection
     * features : [{"type":"Feature","geometry":{"type":"Polygon","coordinates":[[[-118.327666667,33.341716667],[-118.327666667,33.341916667],[-118.32786666700001,33.341916667],[-118.32786666700001,33.341716667],[-118.327666667,33.341716667]]]},"properties":{"e":30.51181200000358}},{"type":"Feature","geometry":{"type":"Polygon","coordinates":[[[-118.32608613566548,33.34403636927165],[-118.32608613566548,33.34423636927165],[-118.32628613566548,33.34423636927165],[-118.32628613566548,33.34403636927165],[-118.32608613566548,33.34403636927165]]]},"properties":{"e":7.2178480000023875}}]
     */

    private String type;
    private List<FeaturesBean> features;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FeaturesBean> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeaturesBean> features) {
        this.features = features;
    }

    public static class FeaturesBean {
        /**
         * type : Feature
         * geometry : {"type":"Polygon","coordinates":[[[-118.327666667,33.341716667],[-118.327666667,33.341916667],[-118.32786666700001,33.341916667],[-118.32786666700001,33.341716667],[-118.327666667,33.341716667]]]}
         * properties : {"e":30.51181200000358}
         */

        private String type;
        private GeometryBean geometry;
        private PropertiesBean properties;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public PropertiesBean getProperties() {
            return properties;
        }

        public void setProperties(PropertiesBean properties) {
            this.properties = properties;
        }

        public static class GeometryBean {
            /**
             * type : Polygon
             * coordinates : [[[-118.327666667,33.341716667],[-118.327666667,33.341916667],[-118.32786666700001,33.341916667],[-118.32786666700001,33.341716667],[-118.327666667,33.341716667]]]
             */

            private String type;
            private List<List<List<Double>>> coordinates;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<List<List<Double>>> getCoordinates() {
                return coordinates;
            }

            public void setCoordinates(List<List<List<Double>>> coordinates) {
                this.coordinates = coordinates;
            }
        }

        public static class PropertiesBean {
            /**
             * e : 30.51181200000358
             */

            private double e;

            public double getE() {
                return e;
            }

            public void setE(double e) {
                this.e = e;
            }
        }
    }
}
