import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetBasedPageRequest implements Pageable
{
    private final int limit;
    private final int offset;

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit the size of the elements to be returned.
     */
    public OffsetBasedPageRequest(int offset, int limit) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero");
        }
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one");
        }
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest(getPageSize(), (int)(getOffset() + getPageSize()));
    }

    public Pageable previous() {
        return hasPrevious() ? new OffsetBasedPageRequest(getPageSize(), (int)(getOffset() - getPageSize())): this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(getPageSize(), 0);
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }
}
