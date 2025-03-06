# overview
1. allow user upload picture and system screening
2. upload picture through url
3. web scrap and create pictures as group
# analyze
1. whenever service include the UGC(user generate content), it should have screening 
2. user upload picture = admin upload + screening
3. admin mannual screening: allow check and filter all documents, output approval or rejectionl.
   also reccord the dialog(admin account and time)
# Review Logic and Database Table Design

## 1. Review Logic

### 1) The review process that administrators can operate:

- By default, images are set to "Pending Review" and can be marked as "Approved" or "Rejected".
- Rejected images can be reviewed again and approved.
- Approved images can be revoked and set to rejected status.

### 2) Automatic review by administrators:
- When an administrator uploads or updates an image, the image is automatically approved.
- The review parameters are auto-filled:
    - The reviewer is set as the creator.
    - The review time is set to the current time.
    - The review reason is "Automatically reviewed by administrator."

### 3) User-submitted images require review:
- When users upload or edit images, the image status is reset to "Pending Review."
- During re-review, the administrator can either reset **all** review parameters or only update the review status.
- Other parameters are not displayed on the frontend but are stored in the backend for historical reference.

### 4) Content visibility control:
- For users, only images marked as "Approved" should be visible.
- Administrators can access all images from the management panel and filter them based on their review status.

**Q: Should we consider a reward/punishment mechanism for reviews?**  
**A:** Since the review process is manually performed by administrators and does not involve complex reward or punishment mechanisms or frequent actions, the impact of erroneous reviews is minimal. Therefore, there is no need to introduce a reward/punishment system.

---

## 2. Database Table Design

To support the review feature, we added review-related fields to the `picture` table and optimized the indexing for better query performance.

### SQL for modifying the table:

```sql
ALTER TABLE picture
-- Add new columns
ADD COLUMN reviewStatus INT DEFAULT 0 NOT NULL COMMENT 'Review Status',
ADD COLUMN reviewMessage VARCHAR(512) NULL COMMENT 'Review Comments',
ADD COLUMN reviewerId BIGINT NULL COMMENT 'Reviewer ID',
ADD COLUMN reviewTime DATETIME NULL COMMENT 'Review Timestamp';

-- Create an index on the reviewStatus column
CREATE INDEX idx_reviewStatus ON picture (reviewStatus);
```
