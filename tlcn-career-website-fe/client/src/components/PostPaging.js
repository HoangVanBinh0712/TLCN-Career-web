
const PostPaging = ({ handlePageChange, currentPage, totalPage }) => {
  return (
    <>
      <div>
        <ul className="pagination">
          <li className="box-paging">
            <p
              className=""
              onClick={() => {
                if (currentPage !== 1) handlePageChange(1);
              }}
              aria-label="Go to first page"
            >
              «
            </p>
          </li>
          <li className="box-paging">
            <p
              className=""
              onClick={() => {
                if (currentPage > 1) handlePageChange(currentPage - 1);
              }}
              aria-label="Go to previous page"
            >
              ⟨
            </p>
          </li>
          <li className="box-paging">
            <p>{currentPage}</p>
          </li>
          <li className="box-paging">
            <p
              className=""
              onClick={() => {
                if (currentPage < totalPage) handlePageChange(currentPage + 1);
              }}
              aria-label="Go to next page"
            >
              ⟩
            </p>
          </li>
          <li className="box-paging">
            <p
              className=""
              onClick={() => {
                if (currentPage !== totalPage) handlePageChange(totalPage);
              }}
              aria-label="Go to last page"
            >
              »
            </p>
          </li>
        </ul>
      </div>
    </>
  );
};

export default PostPaging;
