export interface CategoryDetailType {
  storeName: string;
  category: string;
  body: string;
  latitude: number;
  longitude: number;
  kakao: string;
  contact: string;
  address: string;
  isLike: boolean;
  profileImg: string;
  createdAt: string;
  items: ItemType[];
  storeImages: string[];
};

export interface ItemType {
  itemId: number;
  itemName: string;
  price: number;
  totalTicket: number;
  remainingTicket: number;
}

export interface reserItemsType {
  itemId: number;
  ticketCount: number;
}

export interface reserFormType {
  reservationName: string;
  reservationPhone: string;
  reservationEmail: string;
  reservationDate: string;
  reservationItems: reserItemsType[];
  totalPrice: number;
}

export interface ReviewsType {
  reviewCount: number;
  ratingAvg: number;
  reviews: ReviewType[];
}

export interface ReviewType {
  profileImage: string;
  nickname: string;
  content: string;
  rating: number;
  createdAt: string;
}