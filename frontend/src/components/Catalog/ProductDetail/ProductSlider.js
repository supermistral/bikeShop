import React, { useEffect } from "react";
import { Lazy, Navigation, Pagination } from "swiper";
import { Swiper, SwiperSlide } from "swiper/react";

import "swiper/css";
import "swiper/css/lazy";
import "swiper/css/pagination";
import "swiper/css/navigation";


const ProductSlider = ({ images }) => {

    return (
        <Swiper
            slidesPerView={1}
            loop={true}
            lazy={true}
            pagination={{ clickable: true }}
            navigation={true}
            modules={[Lazy, Pagination, Navigation]}
            className="item-slider"
        >
            {images.map((image, i) => 
                <SwiperSlide key={i} className="item-slider-image">
                    <img data-src={image} className="swiper-lazy" />
                    <div className="swiper-lazy-preloader"></div>
                </SwiperSlide>
            )}
        </Swiper>
    )
}

export default ProductSlider;