import React, { useState } from 'react';
import { useSearchParams } from 'react-router-dom';

const Modal = ({ visible, setVisible, children }) => {
  if (!visible) return null;

  const handleBackdropClick = (e) => {
    // Проверяем, был ли клик выполнен на фоне, а не на содержимом модального окна
    if (e.target === e.currentTarget) {
      setVisible(false); // Закрываем модалку
    }
  };

  return (
    <div onClick={handleBackdropClick}>
    <div className="fixed inset-0 bg-black/50 flex justify-center items-center z-50"
    onClick={handleBackdropClick}
    >
        {children}
    </div>
    </div>
    
  );
};

export default Modal;
