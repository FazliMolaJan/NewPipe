package org.schabi.newpipe.player.playqueue;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

public abstract class PlayQueueItemTouchCallback extends ItemTouchHelper.SimpleCallback {
    private static final int MINIMUM_INITIAL_DRAG_VELOCITY = 10;
    private static final int MAXIMUM_INITIAL_DRAG_VELOCITY = 25;

    public PlayQueueItemTouchCallback() {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT);
    }

    public abstract void onMove(final int sourceIndex, final int targetIndex);

    public abstract void onSwiped(int index);

    @Override
    public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int viewSize,
                                            int viewSizeOutOfBounds, int totalSize,
                                            long msSinceStartScroll) {
        final int standardSpeed = super.interpolateOutOfBoundsScroll(recyclerView, viewSize,
                viewSizeOutOfBounds, totalSize, msSinceStartScroll);
        final int clampedAbsVelocity = Math.max(MINIMUM_INITIAL_DRAG_VELOCITY,
                Math.min(Math.abs(standardSpeed), MAXIMUM_INITIAL_DRAG_VELOCITY));
        return clampedAbsVelocity * (int) Math.signum(viewSizeOutOfBounds);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source,
                          RecyclerView.ViewHolder target) {
        if (source.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        final int sourceIndex = source.getLayoutPosition();
        final int targetIndex = target.getLayoutPosition();
        onMove(sourceIndex, targetIndex);
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        onSwiped(viewHolder.getAdapterPosition());
    }
}
