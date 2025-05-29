// import { Pipe, PipeTransform } from '@angular/core';

// @Pipe({
//   name: 'timeAgo',
//   standalone: true
// })
// export class TimeAgoPipe implements PipeTransform {
//   transform(value: string | Date): string {
//     if (!value) return '';

//     let past: Date;

//     if (typeof value === 'string') {
//       // Cắt bớt phần microseconds nếu có quá 3 chữ số
//       const trimmed = value.replace(/\.\d{3,6}/, match => match.slice(0, 4));
//       const parsed = Date.parse(trimmed);
//       if (isNaN(parsed)) return 'Thời gian không hợp lệ';
//       past = new Date(parsed);
//     } else {
//       past = value;
//     }

//     const now = new Date();
//     const diff = Math.floor((now.getTime() - past.getTime()) / 1000);

//     if (diff < 60) return `${diff} giây trước`;
//     const minutes = Math.floor(diff / 60);
//     if (minutes < 60) return `${minutes} phút trước`;
//     const hours = Math.floor(minutes / 60);
//     if (hours < 24) return `${hours} giờ trước`;
//     const days = Math.floor(hours / 24);
//     if (days < 7) return `${days} ngày trước`;
//     const weeks = Math.floor(days / 7);
//     if (weeks < 4) return `${weeks} tuần trước`;
//     const months = Math.floor(days / 30);
//     if (months < 12) return `${months} tháng trước`;
//     const years = Math.floor(days / 365);
//     return `${years} năm trước`;
//   }
// }
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'timeAgo',
  standalone: true
})
export class TimeAgoPipe implements PipeTransform {
  transform(value: string | Date): string {
    if (!value) return '';

    let past: Date;

    if (typeof value === 'string') {
      // Trim microseconds (keep only 3 digits)
      const trimmed = value.replace(/\.\d{3,6}/, match => match.slice(0, 4));
      const parsed = Date.parse(trimmed);
      if (isNaN(parsed)) return 'Invalid date';
      past = new Date(parsed);
    } else {
      past = value;
    }

    const now = new Date();
    // console.log(now.toLocaleString());
    // sẽ hiển thị theo local time của trình duyệt, Chuỗi "2025-05-20T15:56:05.045387" là ISO 8601
    // Nhưng không có thông tin múi giờ → mặc định sẽ được parse là UTC
    const diff = Math.floor((now.getTime() - past.getTime()) / 1000);

    if (diff < 60) return `${diff} seconds`;
    const minutes = Math.floor(diff / 60);
    if (minutes < 60) return `${minutes} minute${minutes > 1 ? 's' : ''}`;
    const hours = Math.floor(minutes / 60);
    if (hours < 24) return `${hours} hour${hours > 1 ? 's' : ''}`;
    const days = Math.floor(hours / 24);
    if (days < 7) return `${days} day${days > 1 ? 's' : ''}`;
    const weeks = Math.floor(days / 7);
    if (weeks < 4) return `${weeks} week${weeks > 1 ? 's' : ''}`;
    const months = Math.floor(days / 30);
    if (months < 12) return `${months} month${months > 1 ? 's' : ''}`;
    const years = Math.floor(days / 365);
    return `${years} year${years > 1 ? 's' : ''}`;
  }
}

