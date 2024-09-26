import { useEffect, useState } from "react";
import { communityComment } from "../../services/CommunityDetail/CommuniyDetailGet";
import { CommentPost } from "../../services/CommunityDetail/CommunityDetailPost";
import { useParams } from "react-router-dom";
import '../../styles/CommunityDetail/CommunityComment.css'
import '../../styles/CommunityDetail/CommunityDetailBody.css'

function CommunityComment(){
    const {id} = useParams<{id:string}>();
    const Id = Number(id);
    const [commentList, setCommentList] = useState<any>(null);

    useEffect(() => {
        const getCommentList = async () => {
            if(!id){
                return;
            }

            try {
                const commentData = await communityComment(Id);
                console.log(commentData)
                setCommentList(commentData)
            } catch(e){
                console.log(e)
            }
        };

        getCommentList();
    },[id]);

    return(
        <div className='comment-size modal-background' >댓글 창...    </div>
    )
}

export default CommunityComment;