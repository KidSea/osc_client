package com.example.oschina_client.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;

import com.example.oschina_client.R;
import com.example.oschina_client.base.BaseActivity;
import com.example.oschina_client.base.BaseFragment;
import com.example.oschina_client.emoji.OnSendClickListener;
import com.example.oschina_client.fragment.NewsDetailFragment;

/**
 * 详情页 (包括:资讯、博客、软件、回答、动弹)
 * 
 * @author yuxuehai
 * 
 */ 
public class DetailActivity extends BaseActivity implements OnSendClickListener {

    public static final int DISPLAY_NEWS = 0;
    public static final int DISPLAY_BLOG = 1;
    public static final int DISPLAY_SOFTWARE = 2;
    public static final int DISPLAY_POST = 3;
    public static final int DISPLAY_TWEET = 4;
    public static final int DISPLAY_EVENT = 5;
    public static final int DISPLAY_TEAM_ISSUE_DETAIL = 6;
    public static final int DISPLAY_TEAM_DISCUSS_DETAIL = 7;
    public static final int DISPLAY_TEAM_TWEET_DETAIL = 8;
    public static final int DISPLAY_TEAM_DIARY = 9;

    public static final String BUNDLE_KEY_DISPLAY_TYPE = "BUNDLE_KEY_DISPLAY_TYPE";

    private OnSendClickListener currentFragment;
//    public KJEmojiFragment emojiFragment = new KJEmojiFragment();
//    public ToolbarFragment toolFragment = new ToolbarFragment();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.actionbar_title_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        int displayType = getIntent().getIntExtra(BUNDLE_KEY_DISPLAY_TYPE,
                DISPLAY_NEWS);
        BaseFragment fragment = null;
        int actionBarTitle = 0;
        switch (displayType) {
        case DISPLAY_NEWS:
            actionBarTitle = R.string.actionbar_title_news;
            fragment = new NewsDetailFragment();
            break;
        case DISPLAY_BLOG:
            actionBarTitle = R.string.actionbar_title_blog;
//            fragment = new BlogDetailFragment();
            break;
//        case DISPLAY_SOFTWARE:
//            actionBarTitle = R.string.actionbar_title_software;
//            fragment = new SoftwareDetailFragment();
//            break;
//        case DISPLAY_POST:
//            actionBarTitle = R.string.actionbar_title_question;
//            fragment = new PostDetailFragment();
//            break;
//        case DISPLAY_TWEET:
//            actionBarTitle = R.string.actionbar_title_tweet;
//            fragment = new TweetDetailFragment();
//            break;
//        case DISPLAY_EVENT:
//            actionBarTitle = R.string.actionbar_title_event_detail;
//            fragment = new EventDetailFragment();
//            break;
//        case DISPLAY_TEAM_ISSUE_DETAIL:
//            actionBarTitle = R.string.team_issue_detail;
//            fragment = new TeamIssueDetailFragment();
//            break;
//        case DISPLAY_TEAM_DISCUSS_DETAIL:
//            actionBarTitle = R.string.actionbar_title_question;
//            fragment = new TeamDiscussDetailFragment();
//            break;
//        case DISPLAY_TEAM_TWEET_DETAIL:
//            actionBarTitle = R.string.actionbar_dynamic_detail;
//            fragment = new TeamTweetDetailFragment();
//            break;
//        case DISPLAY_TEAM_DIARY:
//            actionBarTitle = R.string.team_diary_detail;
//            fragment = new TeamDiaryDetail();
//            break;
        default:
            break;
        }
        setActionBarTitle(actionBarTitle);
        FragmentTransaction trans = getSupportFragmentManager()
                .beginTransaction();
        trans.replace(R.id.container, fragment);
        trans.commitAllowingStateLoss();
//        if (fragment instanceof OnSendClickListener) {
//            currentFragment = (OnSendClickListener) fragment;
//        } else {
//            currentFragment = new OnSendClickListener() {
//                @Override
//                public void onClickSendButton(Editable str) {}
//
//                @Override
//                public void onClickFlagButton() {}
//            };
//        }
    }

    @Override
    public void onClick(View v) {}

    @Override
    public void initView() {
//        if (currentFragment instanceof TweetDetailFragment
//                || currentFragment instanceof TeamTweetDetailFragment
//                || currentFragment instanceof TeamDiaryDetail
//                || currentFragment instanceof TeamIssueDetailFragment
//                || currentFragment instanceof TeamDiscussDetailFragment) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.emoji_keyboard, emojiFragment).commit();
//        } else {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.emoji_keyboard, toolFragment).commit();
//        }
//        toolFragment.setOnActionClickListener(new OnActionClickListener() {
//            @Override
//            public void onActionClick(ToolAction action) {
//                switch (action) {
//                case ACTION_CHANGE:
//                case ACTION_WRITE_COMMENT:
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .setCustomAnimations(R.anim.footer_menu_slide_in,
//                                    R.anim.footer_menu_slide_out)
//                            .replace(R.id.emoji_keyboard, emojiFragment)
//                            .commit();
//                    break;
//                case ACTION_FAVORITE:
//                    ((BaseDetailFragment) currentFragment)
//                            .handleFavoriteOrNot();
//                    break;
//                case ACTION_REPORT:
//                    ((BaseDetailFragment) currentFragment).onReportMenuClick();
//                    break;
//                case ACTION_SHARE:
//                    ((BaseDetailFragment) currentFragment).handleShare();
//                    break;
//                case ACTION_VIEW_COMMENT:
//                    ((BaseDetailFragment) currentFragment)
//                            .onclickWriteComment();
//                    break;
//                default:
//                    break;
//                }
//            }
//        });

    }

    @Override
    public void initData() {}

    @Override
    public void onClickSendButton(Editable str) {
//        currentFragment.onClickSendButton(str);
//        emojiFragment.clean();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            try {
//                if (emojiFragment.isShowEmojiKeyBoard()) {
//                    emojiFragment.hideAllKeyBoard();
//                    return true;
//                }
//                if (emojiFragment.getEditText().getTag() != null) {
//                    emojiFragment.getEditText().setTag(null);
//                    emojiFragment.getEditText().setHint("说点什么吧");
//                    return true;
//                }
//            } catch (NullPointerException e) {
//            }
//        }
        return super.onKeyDown(keyCode, event);
    }

    public void setCommentCount(int count) {
        try {
//            toolFragment.setCommentCount(count);
        } catch (Exception e) {
        }
    }

    @Override
    public void onClickFlagButton() {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .setCustomAnimations(R.anim.footer_menu_slide_in,
//                        R.anim.footer_menu_slide_out)
//                .replace(R.id.emoji_keyboard, toolFragment).commit();
//        try {
//            toolFragment.setCommentCount(((BaseDetailFragment) currentFragment)
//                    .getCommentCount());
//        } catch (Exception e) {
//        }
    }
}
