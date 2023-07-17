import { useEffect, useRef } from "react";
import {
  PaymentWidgetInstance,
  loadPaymentWidget,
  ANONYMOUS
} from "@tosspayments/payment-widget-sdk";
import { nanoid } from "nanoid";
import { useRecoilValue } from "recoil";
import { totalPrice } from "../store/categoryDetailAtom";

const selector = "#payment-widget";
const clientKey = 'test_ck_dP9BRQmyarY0eEomwzZVJ07KzLNk';
const secretKey = 'test_sk_7DLJOpm5Qrl0eEYvlG0rPNdxbWnY';
const customerKey = "YbX2HuSlsC9uVJW6NMRMj";

function Payments() {
  const paymentWidgetRef = useRef<PaymentWidgetInstance | null>(null);
  const paymentMethodsWidgetRef = useRef<ReturnType<
    PaymentWidgetInstance["renderPaymentMethods"]
    > | null>(null);
  const price = useRecoilValue(totalPrice);

  useEffect(() => {
    (async () => {
      // ------  결제위젯 초기화 ------
      // 비회원 결제에는 customerKey 대신 ANONYMOUS를 사용하세요.
      const paymentWidget = await loadPaymentWidget(clientKey, customerKey); // 회원 결제
      // const paymentWidget = await loadPaymentWidget(clientKey, ANONYMOUS); // 비회원 결제

      // ------  결제위젯 렌더링 ------
      // https://docs.tosspayments.com/reference/widget-sdk#renderpaymentmethods선택자-결제-금액-옵션
      const paymentMethodsWidget = paymentWidget.renderPaymentMethods(
        selector,
        { value: 200 }
      );

      // ------  이용약관 렌더링 ------
      // https://docs.tosspayments.com/reference/widget-sdk#renderagreement선택자
      paymentWidget.renderAgreement("#agreement");

      paymentWidgetRef.current = paymentWidget;
      paymentMethodsWidgetRef.current = paymentMethodsWidget;
    })();
  }, []);

  useEffect(() => {
    const paymentMethodsWidget = paymentMethodsWidgetRef.current;

    if (paymentMethodsWidget == null) {
      return;
    }

    // ------ 금액 업데이트 ------
    // https://docs.tosspayments.com/reference/widget-sdk#updateamount결제-금액
    paymentMethodsWidget.updateAmount(
      price,
      paymentMethodsWidget.UPDATE_REASON.COUPON
    );
  }, [price]);

  return (
    <section className="w-[600px] mx-auto mt-[200px]">
      <span className="text-xl font-bold text-[#4771B7]">총 결제금액: {`${price.toLocaleString()}원`}</span>
      <div id="payment-widget" />
      <div id="agreement" />
      <button
        className="border-2 border-[#4771B7] px-3 py-1 font-bold rounded-[10px] text-[#4771B7] mx-auto block hover:bg-[#d8ecfc] duration-500"
        onClick={async () => {
          const paymentWidget = paymentWidgetRef.current;
          try {
            // ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
            // https://docs.tosspayments.com/reference/widget-sdk#requestpayment결제-정보
            await paymentWidget?.requestPayment({
              orderId: nanoid(),
              orderName: "토스 티셔츠 외 2건",
              customerName: "김토스",
              customerEmail: "customer123@gmail.com",
              successUrl: `${window.location.origin}/home`,
              failUrl: `${window.location.origin}/fail`,
            });
          } catch (error) {
            // 에러 처리하기
            console.error(error);
          }
        }}
      >
        결제하기
      </button>
    </section>
  );
}

export default Payments;