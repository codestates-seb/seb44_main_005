import { 
    NoWishTitle 
} from "../../styles/MyPage/WishList";

function NothingComponent({ title, description}) {
    return (
        <>
          <NoWishTitle>{title}</NoWishTitle>
          <p>{description}</p>
        </>
    );
};

export default NothingComponent;